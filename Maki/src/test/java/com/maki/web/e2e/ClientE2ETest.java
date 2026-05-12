package com.maki.web.e2e;

import static org.junit.jupiter.api.Assertions.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientE2ETest {

  // ── URLs ─────────────────────────────────────────────────────────────────
  private static final String BASE_URL = "http://localhost:4200";
  private static final String LOGIN_URL = BASE_URL + "/client/log-in";
  private static final String MENU_URL = BASE_URL + "/plate/menu";

  // ── Credenciales del cliente precargado por el Dataloader ────────────────
  // El Dataloader del perfil "test" crea: acha@acha.dev / eveyzoe  (id=1)
  private static final String CLIENT_EMAIL = "acha@acha.dev";
  private static final String CLIENT_PASSWORD = "eveyzoe";
  private static String actualClientId = "1";

  // ── Credenciales del operador precargado por el Dataloader ────────────────
  private static final String OPERATOR_USERNAME = "carlos.gomez@example.com";
  private static final String OPERATOR_PASSWORD = "123456";

  // ── Tiempo máximo de espera para que los datos lleguen del backend ────────
  private static final int WAIT_SECONDS = 10;

  // ── Selenium ──────────────────────────────────────────────────────────────
  private WebDriver driver;
  private WebDriverWait wait;

  // =========================================================================
  // CONFIGURACIÓN — se ejecuta antes de cada prueba
  // =========================================================================

  @BeforeEach
  public void setUp() {
    // Configura automáticamente el driver de Chrome
    WebDriverManager.chromedriver().setup();

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--disable-extensions");
    options.addArguments("--disable-notifications");
    options.addArguments("--headless");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--disable-gpu");
    options.addArguments("--window-size=1920,1080");

    driver = new ChromeDriver(options);
    wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));

    // Limpiar el localStorage antes de cada prueba para que no haya
    // sesión ni carrito residual de pruebas anteriores
    driver.get(BASE_URL);
    ((JavascriptExecutor) driver).executeScript("localStorage.clear();");
  }

  // =========================================================================
  // CIERRE — se ejecuta después de cada prueba
  // =========================================================================

  @AfterEach
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  // =========================================================================
  // CASO 1 — SIMPLE: Login con credenciales válidas
  // =========================================================================

  /**
   * El usuario llega al login, ingresa sus credenciales correctas
   * y debe ser redirigido a su página de sesión (/client/{id}).
   */
  @Test
  @Order(1)
  public void caso1_login_ConCredencialesValidas_RedireccionaAlPerfil() {
    // Abrir la página de login
    driver.get(LOGIN_URL);

    // Esperar a que el formulario esté disponible
    wait.until(
      ExpectedConditions.presenceOfElementLocated(By.id("input-email"))
    );

    // Llenar el formulario
    driver.findElement(By.id("input-email")).sendKeys(CLIENT_EMAIL);
    driver.findElement(By.id("input-password")).sendKeys(CLIENT_PASSWORD);

    // Hacer clic en "Iniciar sesión"
    driver.findElement(By.id("btn-login")).click();

    // Esperar que la URL cambie al perfil del cliente y capturar el ID
    wait.until(ExpectedConditions.urlMatches(".*/client/\\d+$"));
    String urlActual = driver.getCurrentUrl();
    actualClientId = urlActual.substring(urlActual.lastIndexOf("/") + 1);

    assertTrue(
      urlActual.contains("/client/" + actualClientId),
      "Después del login debe redirigir a la página del cliente"
    );

    // Verificar que el email del cliente aparece en pantalla
    // (el componente ClientSessionComponent muestra client?.email)
    wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.xpath("//*[contains(text(), '" + CLIENT_EMAIL + "')]")
      )
    );

    WebElement textoEmail = driver.findElement(
      By.xpath("//*[contains(text(), '" + CLIENT_EMAIL + "')]")
    );

    assertTrue(
      textoEmail.isDisplayed(),
      "El email del cliente debe aparecer en la pantalla de bienvenida"
    );
  }

  /**
   * Prueba negativa: credenciales incorrectas deben mostrar una alerta.
   * (El servicio devuelve 400 y el componente llama a alert())
   */
  @Test
  @Order(2)
  public void caso1_login_ConPasswordIncorrecta_MuestraAlerta() {
    driver.get(LOGIN_URL);

    wait.until(
      ExpectedConditions.presenceOfElementLocated(By.id("input-email"))
    );

    driver.findElement(By.id("input-email")).sendKeys(CLIENT_EMAIL);
    driver
      .findElement(By.id("input-password"))
      .sendKeys("password_incorrecta_123");
    driver.findElement(By.id("btn-login")).click();

    // Esperar a que aparezca el alert del navegador
    try {
      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      String mensajeAlerta = alert.getText();
      alert.accept(); // Cerrar el alert

      assertTrue(
        mensajeAlerta.toLowerCase().contains("credencial") ||
          mensajeAlerta.toLowerCase().contains("incorrect"),
        "La alerta debe indicar credenciales incorrectas"
      );
    } catch (TimeoutException e) {
      // Si no aparece alert, verificar que no se redirigió al perfil
      assertFalse(
        driver.getCurrentUrl().matches(".*/client/\\d+$"),
        "Con credenciales incorrectas NO debe redirigir al perfil"
      );
    }
  }

  // =========================================================================
  // CASO 2 — MEDIO: Ir al menú y agregar 2 platos con 2 adicionales c/u,
  //                  luego verificar el carrito (ítems y total dinámico)
  // =========================================================================

  /**
   * El usuario (ya logueado) va al menú, hace clic en los primeros 2 platos
   * disponibles, selecciona 2 adicionales en cada uno, los agrega al carrito
   * y verifica:
   *   - Que el carrito tiene exactamente 2 ítems.
   *   - Que cada ítem tiene 2 adicionales visibles.
   *   - Que el total del carrito es mayor a 0 (no hardcodeado).
   */
  @Test
  @Order(3)
  public void caso2_menu_Agregar2Platoscon2AdicionalesYVerificarCarrito() {
    // ── Paso 1: Login ────────────────────────────────────────────────────
    loginComoCliente();

    // ── Paso 2: Ir al menú ───────────────────────────────────────────────
    driver.get(MENU_URL);

    // Esperar a que los platos carguen
    wait.until(
      ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.className("btn-ver-plato")
      )
    );

    List<WebElement> botonesVerPlato = driver.findElements(
      By.className("btn-ver-plato")
    );

    assertTrue(
      botonesVerPlato.size() >= 2,
      "Debe haber al menos 2 platos en el menú"
    );

    // ── Paso 3: Abrir el PRIMER plato y agregar con 2 adicionales ────────
    agregarPlatoConAdicionales(botonesVerPlato, 0);

    // ── Paso 4: Volver al menú y abrir el SEGUNDO plato ──────────────────
    driver.get(MENU_URL);

    wait.until(
      ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.className("btn-ver-plato")
      )
    );

    botonesVerPlato = driver.findElements(By.className("btn-ver-plato"));
    agregarPlatoConAdicionales(botonesVerPlato, 1);

    // ── Paso 5: Abrir el carrito ─────────────────────────────────────────
    // El carrito se abre al hacer clic en el botón de la cabecera
    WebElement btnCarrito = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.cssSelector("button.header-button")
      )
    );
    btnCarrito.click();

    // ── Paso 6: Esperar a que el carrito muestre los ítems ───────────────
    wait.until(
      ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.className("carrito-item")
      )
    );

    List<WebElement> itemsEnCarrito = driver.findElements(
      By.className("carrito-item")
    );

    // ASSERT 1: El carrito debe tener exactamente 2 ítems
    assertEquals(
      2,
      itemsEnCarrito.size(),
      "El carrito debe contener exactamente 2 platos"
    );

    // ASSERT 2: Cada ítem debe mostrar exactamente 2 adicionales
    for (WebElement item : itemsEnCarrito) {
      List<WebElement> adicionalesDelItem = item.findElements(
        By.className("carrito-item-adicional")
      );
      assertEquals(
        2,
        adicionalesDelItem.size(),
        "Cada ítem del carrito debe mostrar 2 adicionales"
      );
    }

    // ASSERT 3: El total debe ser mayor a 0 (calculado desde la página, no hardcodeado)
    WebElement elementoTotal = wait.until(
      ExpectedConditions.presenceOfElementLocated(By.id("carrito-total"))
    );

    // Esperar a que el texto del total no esté vacío
    wait.until(driver ->
      !elementoTotal.getText().replaceAll("[^0-9]", "").isEmpty()
    );

    String textoTotal = elementoTotal.getText();
    String soloNumeros = textoTotal.replaceAll("[^0-9]", "");
    long totalNumerico = Long.parseLong(soloNumeros);

    assertTrue(
      totalNumerico > 0,
      "El total del carrito debe ser mayor a 0. Texto encontrado: " + textoTotal
    );
  }

  // =========================================================================
  // CASO 3 — COMPLEJO: Flujo completo multi-pestaña con operador
  //   Cliente: login → menú → agregar 2 platos con 2 adicionales → verificar
  //            carrito → confirmar pedido.
  //   Operador (otra pestaña): login → pedidos → cambiar estado → verificar
  //            que el cliente ve el cambio → asignar domiciliario → completar.
  //   Cliente: historial de pedidos completados → verificar productos,
  //            adicionales y total calculado dinámicamente.
  // =========================================================================

  /**
   * Flujo E2E completo del sprint:
   *  1. Login del cliente.
   *  2. Ir al menú y agregar 2 platos con 2 adicionales cada uno,
   *     capturando precios de platos y adicionales desde la UI.
   *  3. Verificar el carrito (ítems, nombres, adicionales, total dinámico).
   *  4. Confirmar el pedido.
   *  5. En otra pestaña, el operador inicia sesión y va a pedidos.
   *  6. El operador selecciona el nuevo pedido y cambia su estado.
   *  7. Se verifica en la pestaña del cliente que aparezca el cambio.
   *  8. El operador sigue cambiando estado, asigna domiciliario y completa
   *     el pedido.
   *  9. El cliente ingresa al historial de pedidos completados y revisa
   *     que el pedido tenga todos los productos y adicionales.
   * 10. Verificar que la suma a pagar es acorde a los productos
   *     (no se quema el valor del assert).
   */
  @Test
  @Order(4)
  public void caso3_flujoCompleto_LoginAgregarPedidoYVerificarHistorial()
    throws InterruptedException {
    // ══════════════════════════════════════════════════════════════════════
    // FASE A — CLIENTE: Login, menú, carrito y confirmar pedido
    // ══════════════════════════════════════════════════════════════════════

    // ── PASO 1: Login del cliente (el ID se captura en el método) ──────
    loginComoCliente();

    // ── PASO 2: Ir al menú ─────────────────────────────────────────────
    driver.get(MENU_URL);

    wait.until(
      ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.className("btn-ver-plato")
      )
    );

    List<WebElement> botonesVerPlato = driver.findElements(
      By.className("btn-ver-plato")
    );

    assertTrue(
      botonesVerPlato.size() >= 2,
      "Debe haber al menos 2 platos en el menú"
    );

    // ── PASO 3: Agregar PRIMER plato con 2 adicionales ─────────────────
    //    Se captura nombre + precios desde la UI para el assert dinámico.
    long[] preciosCapturados1 = new long[1]; // [0] = precio plato
    List<Long> preciosAdicionales1 = new java.util.ArrayList<>();
    String nombrePlato1 = agregarPlatoCapturandoPrecios(
      botonesVerPlato,
      0,
      preciosCapturados1,
      preciosAdicionales1
    );
    assertFalse(
      nombrePlato1.isEmpty(),
      "El nombre del plato 1 no debe estar vacío"
    );

    // ── PASO 4: Volver al menú y agregar SEGUNDO plato ─────────────────
    driver.get(MENU_URL);
    wait.until(
      ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.className("btn-ver-plato")
      )
    );
    botonesVerPlato = driver.findElements(By.className("btn-ver-plato"));

    long[] preciosCapturados2 = new long[1];
    List<Long> preciosAdicionales2 = new java.util.ArrayList<>();
    String nombrePlato2 = agregarPlatoCapturandoPrecios(
      botonesVerPlato,
      1,
      preciosCapturados2,
      preciosAdicionales2
    );
    assertFalse(
      nombrePlato2.isEmpty(),
      "El nombre del plato 2 no debe estar vacío"
    );

    // ── PASO 5: Calcular el total esperado dinámicamente ───────────────
    long totalEsperado =
      preciosCapturados1[0] +
      preciosAdicionales1.stream().mapToLong(Long::longValue).sum() +
      preciosCapturados2[0] +
      preciosAdicionales2.stream().mapToLong(Long::longValue).sum();

    assertTrue(
      totalEsperado > 0,
      "El total esperado calculado desde la UI debe ser mayor a 0"
    );

    // ── PASO 6: Abrir el carrito ───────────────────────────────────────
    WebElement btnCarrito = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.cssSelector("button.header-button")
      )
    );
    btnCarrito.click();

    wait.until(
      ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.className("carrito-item")
      )
    );

    // ASSERT: El carrito tiene exactamente 2 ítems
    List<WebElement> itemsCarrito = driver.findElements(
      By.className("carrito-item")
    );
    assertEquals(
      2,
      itemsCarrito.size(),
      "El carrito debe contener exactamente 2 platos"
    );

    // ASSERT: Los nombres de los platos están en el carrito
    List<WebElement> nombresEnCarrito = driver.findElements(
      By.className("carrito-item-nombre")
    );
    List<String> nombresTexto = nombresEnCarrito
      .stream()
      .map(el -> el.getAttribute("textContent").trim())
      .toList();

    assertTrue(
      nombresTexto.stream().anyMatch(n -> n.equals(nombrePlato1)),
      "El carrito debe contener el plato: " + nombrePlato1
    );
    assertTrue(
      nombresTexto.stream().anyMatch(n -> n.equals(nombrePlato2)),
      "El carrito debe contener el plato: " + nombrePlato2
    );

    // ASSERT: Cada ítem del carrito tiene 2 adicionales
    for (WebElement item : itemsCarrito) {
      List<WebElement> adicionalesDelItem = item.findElements(
        By.className("carrito-item-adicional")
      );
      assertEquals(
        2,
        adicionalesDelItem.size(),
        "Cada ítem del carrito debe mostrar 2 adicionales"
      );
    }

    // ASSERT: El total del carrito coincide con el total calculado
    WebElement elementoTotalCarrito = wait.until(
      ExpectedConditions.presenceOfElementLocated(By.id("carrito-total"))
    );
    wait.until(d ->
      !elementoTotalCarrito.getText().replaceAll("[^0-9]", "").isEmpty()
    );

    String textoTotalCarrito = elementoTotalCarrito.getText();
    long totalCarritoNumerico = Long.parseLong(
      textoTotalCarrito.replaceAll("[^0-9]", "")
    );

    assertEquals(
      totalEsperado,
      totalCarritoNumerico,
      "El total del carrito (" +
        totalCarritoNumerico +
        ") debe coincidir con la suma de precios capturados (" +
        totalEsperado +
        ")"
    );

    // ── PASO 7: Confirmar el pedido ────────────────────────────────────
    WebElement btnPagar = wait.until(
      ExpectedConditions.elementToBeClickable(By.id("btn-pagar"))
    );
    btnPagar.click();

    // Esperar la alerta de confirmación "Pedido creado!"
    try {
      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      String mensajeAlert = alert.getText();
      assertTrue(
        mensajeAlert.toLowerCase().contains("pedido"),
        "La alerta debe confirmar la creación del pedido"
      );
      alert.accept();
    } catch (TimeoutException e) {
      // Si no hay alert, continuar
    }

    // Guardar el handle de la pestaña del cliente
    String pestanaCliente = driver.getWindowHandle();

    // ══════════════════════════════════════════════════════════════════════
    // FASE B — OPERADOR (nueva pestaña): Login, ir a pedidos,
    //          cambiar estado y asignar domiciliario
    // ══════════════════════════════════════════════════════════════════════

    // ── PASO 8: Abrir nueva pestaña para el operador ──────────────────
    ((JavascriptExecutor) driver).executeScript("window.open();");
    java.util.Set<String> handles = driver.getWindowHandles();
    String pestanaOperador = handles
      .stream()
      .filter(h -> !h.equals(pestanaCliente))
      .findFirst()
      .orElseThrow();
    driver.switchTo().window(pestanaOperador);

    // ── PASO 9: Login del operador ────────────────────────────────────
    driver.get(BASE_URL + "/operator/log-in");
    wait.until(
      ExpectedConditions.presenceOfElementLocated(By.name("username"))
    );

    driver.findElement(By.name("username")).sendKeys(OPERATOR_USERNAME);
    driver.findElement(By.name("password")).sendKeys(OPERATOR_PASSWORD);
    driver.findElement(By.cssSelector("button[type='submit']")).click();

    // Esperar redirección al gateway del operador
    wait.until(ExpectedConditions.urlContains("/operator/gateway"));

    // ── PASO 10: Ir a la sección de pedidos ───────────────────────────
    driver.get(BASE_URL + "/purchase-order/adminview");

    // Esperar a que cargue la tabla de pedidos y aparezca el pedido del cliente
    wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.xpath(
          "//table//tbody//tr[contains(., 'ID: " + actualClientId + "')]"
        )
      )
    );

    // Buscar el botón "View" de la fila que pertenece al cliente de prueba.
    List<WebElement> filasOrden = driver.findElements(
      By.cssSelector("table tbody tr")
    );

    // Buscamos la fila que contenga el ID del cliente
    WebElement filaDelPedido = null;
    for (WebElement fila : filasOrden) {
      if (fila.getText().contains("ID: " + actualClientId)) {
        filaDelPedido = fila;
      }
    }
    assertNotNull(
      filaDelPedido,
      "Debe existir un pedido del cliente con ID " + actualClientId
    );

    // Hacer clic en "View" dentro de esa fila usando JS para evitar bloqueos
    WebElement btnView = filaDelPedido.findElement(
      By.cssSelector("button.bg-green-500")
    );
    ((JavascriptExecutor) driver).executeScript(
      "arguments[0].click();",
      btnView
    );

    // Esperar a que cargue la vista individual del pedido
    wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.cssSelector("select[name='status']")
      )
    );

    // ── PASO 11: Cambiar estado a "preparation" ───────────────────────
    WebElement selectEstado = driver.findElement(
      By.cssSelector("select[name='status']")
    );
    new org.openqa.selenium.support.ui.Select(selectEstado).selectByValue(
      "preparation"
    );

    // Esperar a que el backend procese la actualización
    Thread.sleep(1500);

    // ── PASO 12: Verificar en la pestaña del CLIENTE que el estado cambió
    driver.switchTo().window(pestanaCliente);
    driver.get(BASE_URL + "/client/orders/" + actualClientId);

    wait.until(
      ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.className("orden-card")
      )
    );

    // La orden más reciente (primera card, ordenadas desc)
    WebElement ordenClienteActual = driver
      .findElements(By.className("orden-card"))
      .get(0);

    WebElement estadoEnCliente = ordenClienteActual.findElement(
      By.className("orden-status")
    );

    assertEquals(
      "preparation",
      estadoEnCliente.getText().trim(),
      "El estado en la pestaña del cliente debe ser 'preparation' después del cambio del operador"
    );

    // ── PASO 13: Volver a la pestaña del operador ─────────────────────
    driver.switchTo().window(pestanaOperador);

    // Recargar la vista del pedido para obtener estado actual
    driver.navigate().refresh();
    wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.cssSelector("select[name='status']")
      )
    );

    // ── PASO 14: Cambiar estado a "sent" ──────────────────────────────
    selectEstado = driver.findElement(By.cssSelector("select[name='status']"));
    new org.openqa.selenium.support.ui.Select(selectEstado).selectByValue(
      "sent"
    );
    Thread.sleep(1500);

    // ── PASO 15: Asignar domiciliario ─────────────────────────────────
    driver.navigate().refresh();
    wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.cssSelector("select[name='status']")
      )
    );

    // Seleccionar el primer domiciliario disponible del dropdown
    WebElement selectDomiciliario = driver.findElement(
      By.cssSelector("select.text-xs")
    );
    List<WebElement> opcionesDomiciliario = selectDomiciliario.findElements(
      By.tagName("option")
    );
    assertTrue(
      opcionesDomiciliario.size() >= 1,
      "Debe haber al menos un domiciliario disponible"
    );
    // Seleccionar el último domiciliario disponible (evitar los que ya están busy)
    new org.openqa.selenium.support.ui.Select(selectDomiciliario).selectByIndex(
      opcionesDomiciliario.size() - 1
    );
    Thread.sleep(1500);

    // ── PASO 16: Cambiar estado a "delivered" ─────────────────────────
    driver.navigate().refresh();
    wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.cssSelector("select[name='status']")
      )
    );
    selectEstado = driver.findElement(By.cssSelector("select[name='status']"));
    new org.openqa.selenium.support.ui.Select(selectEstado).selectByValue(
      "delivered"
    );
    Thread.sleep(1500);

    // ── PASO 17: Cambiar estado a "completed" ─────────────────────────
    driver.navigate().refresh();
    wait.until(
      ExpectedConditions.presenceOfElementLocated(
        By.cssSelector("select[name='status']")
      )
    );
    selectEstado = driver.findElement(By.cssSelector("select[name='status']"));
    new org.openqa.selenium.support.ui.Select(selectEstado).selectByValue(
      "completed"
    );
    Thread.sleep(1500);

    // ══════════════════════════════════════════════════════════════════════
    // FASE C — CLIENTE: Verificar pedido completado en el historial
    // ══════════════════════════════════════════════════════════════════════

    // ── PASO 18: Volver a la pestaña del cliente ──────────────────────
    driver.switchTo().window(pestanaCliente);
    driver.get(BASE_URL + "/client/orders/" + actualClientId);

    wait.until(
      ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.className("orden-card")
      )
    );

    // Buscar la orden con estado "completed" (la que acabamos de completar)
    List<WebElement> todasLasOrdenes = driver.findElements(
      By.className("orden-card")
    );

    WebElement ordenCompletada = null;
    for (WebElement orden : todasLasOrdenes) {
      WebElement estadoBadge = orden.findElement(By.className("orden-status"));
      if (estadoBadge.getText().trim().equals("completed")) {
        ordenCompletada = orden;
        break;
      }
    }

    assertNotNull(
      ordenCompletada,
      "Debe existir una orden con estado 'completed' en el historial del cliente"
    );

    // ── PASO 19: Verificar que la orden tiene los platos correctos ─────
    List<WebElement> itemsDeOrden = ordenCompletada.findElements(
      By.className("orden-item-nombre")
    );
    List<String> nombresItemsOrden = itemsDeOrden
      .stream()
      .map(WebElement::getText)
      .toList();

    assertTrue(
      nombresItemsOrden.contains(nombrePlato1),
      "La orden completada debe tener el plato: " +
        nombrePlato1 +
        " pero solo contiene: " +
        nombresItemsOrden
    );
    assertTrue(
      nombresItemsOrden.contains(nombrePlato2),
      "La orden completada debe tener el plato: " +
        nombrePlato2 +
        " pero solo contiene: " +
        nombresItemsOrden
    );

    // ── PASO 20: Verificar que cada ítem tiene al menos 2 adicionales ──
    List<WebElement> todosLosItems = ordenCompletada.findElements(
      By.className("orden-item")
    );

    for (WebElement item : todosLosItems) {
      List<WebElement> adicionalesDelItem = item.findElements(
        By.className("orden-adicional-tag")
      );
      assertTrue(
        adicionalesDelItem.size() >= 2,
        "Cada ítem de la orden completada debe tener al menos 2 adicionales"
      );
    }

    // ── PASO 21: Verificar que el total del historial coincide ─────────
    //    con el total calculado dinámicamente (no hardcodeado).
    WebElement elementoTotalHistorial = ordenCompletada.findElement(
      By.className("orden-total")
    );
    String textoTotalHistorial = elementoTotalHistorial.getText();
    long totalHistorialNumerico = Long.parseLong(
      textoTotalHistorial.replaceAll("[^0-9]", "")
    );

    assertEquals(
      totalEsperado,
      totalHistorialNumerico,
      "El total del historial (" +
        totalHistorialNumerico +
        ") debe coincidir con la suma de precios capturados (" +
        totalEsperado +
        "). Texto encontrado: " +
        textoTotalHistorial
    );
  }

  // =========================================================================
  // MÉTODOS DE APOYO (privados)
  // =========================================================================

  /**
   * Hace login como el cliente de prueba y espera la redirección.
   */
  private void loginComoCliente() {
    driver.get(LOGIN_URL);
    wait.until(
      ExpectedConditions.presenceOfElementLocated(By.id("input-email"))
    );

    driver.findElement(By.id("input-email")).sendKeys(CLIENT_EMAIL);
    driver.findElement(By.id("input-password")).sendKeys(CLIENT_PASSWORD);
    driver.findElement(By.id("btn-login")).click();

    // Esperar a que la URL cambie al perfil y capturar el ID real
    wait.until(ExpectedConditions.urlMatches(".*/client/\\d+$"));
    String url = driver.getCurrentUrl();
    actualClientId = url.substring(url.lastIndexOf("/") + 1);
  }

  /**
   * Hace clic en el botón del plato indicado, selecciona 2 adicionales y lo agrega al pedido.
   *
   * @param botonesVerPlato Lista de botones "+" del menú
   * @param indicePlato     Posición (0-based) del plato a abrir
   */
  private void agregarPlatoConAdicionales(
    List<WebElement> botonesVerPlato,
    int indicePlato
  ) {
    agregarPlatoConAdicionalesYObtenerNombre(botonesVerPlato, indicePlato);
  }

  /**
   * Hace clic en el botón del plato indicado, selecciona 2 adicionales, lo agrega al pedido
   * y retorna el nombre del plato leído desde la página del plato (id="plate-name").
   * Esto garantiza que el nombre capturado corresponde exactamente al plato abierto,
   * evitando desajustes de índice entre plate-card-name y btn-ver-plato en el menú.
   *
   * @param botonesVerPlato Lista de botones "+" del menú
   * @param indicePlato     Posición (0-based) del plato a abrir
   * @return Nombre del plato leído desde la página del plato
   */
  private String agregarPlatoConAdicionalesYObtenerNombre(
    List<WebElement> botonesVerPlato,
    int indicePlato
  ) {
    // Hacer clic en el botón "+" del plato indicado
    botonesVerPlato.get(indicePlato).click();

    // Esperar a que cargue la página del plato
    wait.until(
      ExpectedConditions.presenceOfElementLocated(By.id("plate-name"))
    );

    // Capturar el nombre del plato desde la página (fuente de verdad)
    String nombrePlato = driver.findElement(By.id("plate-name")).getText();

    // Buscar los checkboxes de adicionales
    List<WebElement> checkboxes = driver.findElements(
      By.className("additional-checkbox")
    );

    if (checkboxes.size() >= 2) {
      // Seleccionar los primeros 2 adicionales
      if (!checkboxes.get(0).isSelected()) {
        checkboxes.get(0).click();
      }
      if (!checkboxes.get(1).isSelected()) {
        checkboxes.get(1).click();
      }
    }
    // Si hay menos de 2 adicionales disponibles, seleccionar los que haya
    else if (checkboxes.size() == 1) {
      if (!checkboxes.get(0).isSelected()) {
        checkboxes.get(0).click();
      }
    }

    // Hacer clic en "Agregar al pedido"
    WebElement btnAgregar = wait.until(
      ExpectedConditions.elementToBeClickable(By.id("btn-add-to-order"))
    );
    btnAgregar.click();

    // Esperar el alert de confirmación
    try {
      wait.until(ExpectedConditions.alertIsPresent());
      driver.switchTo().alert().accept();
    } catch (TimeoutException e) {
      // Si no hay alert, continuar normalmente
    }

    return nombrePlato;
  }

  /**
   * Similar a agregarPlatoConAdicionalesYObtenerNombre pero además captura
   * el precio del plato y los precios de los 2 adicionales seleccionados
   * para poder calcular el total esperado sin hardcodear valores.
   *
   * @param botonesVerPlato    Lista de botones "+" del menú
   * @param indicePlato        Posición (0-based) del plato a abrir
   * @param precioPlato        Array de 1 elemento donde se guarda el precio del plato
   * @param preciosAdicionales Lista donde se agregan los precios de los adicionales seleccionados
   * @return Nombre del plato leído desde la página del plato
   */
  private String agregarPlatoCapturandoPrecios(
    List<WebElement> botonesVerPlato,
    int indicePlato,
    long[] precioPlato,
    List<Long> preciosAdicionales
  ) {
    // Hacer clic en el botón "+" del plato indicado
    botonesVerPlato.get(indicePlato).click();

    // Esperar a que cargue la página del plato
    wait.until(
      ExpectedConditions.presenceOfElementLocated(By.id("plate-name"))
    );

    // Capturar el nombre del plato
    String nombrePlato = driver.findElement(By.id("plate-name")).getText();

    // El precio del plato se obtendrá del carrito después de agregarlo.
    // Marcamos como pendiente.
    precioPlato[0] = 0;

    // Buscar los checkboxes de adicionales y sus precios
    List<WebElement> checkboxItems = driver.findElements(
      By.className("additional-checkbox-item")
    );
    List<WebElement> checkboxes = driver.findElements(
      By.className("additional-checkbox")
    );

    int seleccionados = 0;
    for (int i = 0; i < checkboxes.size() && seleccionados < 2; i++) {
      if (!checkboxes.get(i).isSelected()) {
        checkboxes.get(i).click();
      }
      // Capturar el precio del adicional desde el texto del label
      if (i < checkboxItems.size()) {
        WebElement precioLabel = checkboxItems
          .get(i)
          .findElement(By.className("additional-price"));
        // El texto es algo como "(+$3000)" — extraemos los dígitos
        String textoPrecio = precioLabel.getText().replaceAll("[^0-9]", "");
        if (!textoPrecio.isEmpty()) {
          preciosAdicionales.add(Long.parseLong(textoPrecio));
        }
      }
      seleccionados++;
    }

    // Hacer clic en "Agregar al pedido"
    WebElement btnAgregar = wait.until(
      ExpectedConditions.elementToBeClickable(By.id("btn-add-to-order"))
    );
    btnAgregar.click();

    // Esperar el alert de confirmación
    try {
      wait.until(ExpectedConditions.alertIsPresent());
      driver.switchTo().alert().accept();
    } catch (TimeoutException e) {
      // Si no hay alert, continuar normalmente
    }

    // Obtener el precio del plato desde el carrito
    // Abrir el carrito para leer el precio del item recién agregado
    WebElement btnCarritoTemp = wait.until(
      ExpectedConditions.elementToBeClickable(
        By.cssSelector("button.header-button")
      )
    );
    btnCarritoTemp.click();

    wait.until(
      ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.className("carrito-item")
      )
    );

    // El último item del carrito es el que acabamos de agregar
    List<WebElement> items = driver.findElements(By.className("carrito-item"));
    WebElement ultimoItem = items.get(items.size() - 1);

    // Esperar a que el precio del item se renderice (no vacío)
    WebElement precioItemEl = ultimoItem.findElement(
      By.className("carrito-item-precio")
    );
    wait.until(d -> {
      String txt = precioItemEl.getText().replaceAll("[^0-9]", "");
      return !txt.isEmpty();
    });

    // El precio del item incluye plato + adicionales (qty=1).
    // Restamos los adicionales para obtener el precio del plato.
    String textoPrecioItem = precioItemEl.getText().replaceAll("[^0-9]", "");
    long precioItemTotal = Long.parseLong(textoPrecioItem);
    long sumaAdicionales = preciosAdicionales
      .stream()
      .mapToLong(Long::longValue)
      .sum();
    precioPlato[0] = precioItemTotal - sumaAdicionales;

    // Cerrar el carrito para permitir futuras interacciones
    WebElement btnCerrarCarrito = wait.until(
      ExpectedConditions.elementToBeClickable(By.id("btn-cerrar-carrito"))
    );
    btnCerrarCarrito.click();

    // Esperar a que el carrito desaparezca
    wait.until(
      ExpectedConditions.invisibilityOfElementLocated(
        By.className("shopping-cart-button")
      )
    );

    return nombrePlato;
  }
}
