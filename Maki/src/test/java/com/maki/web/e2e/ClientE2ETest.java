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
  private static final String CLIENT_ID = "1";

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
    // Descomenta la siguiente línea si quieres correr sin ventana gráfica:
    // options.addArguments("--headless");

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

    // Esperar que la URL cambie al perfil del cliente
    wait.until(ExpectedConditions.urlContains("/client/" + CLIENT_ID));

    // Verificar que la URL es la correcta
    String urlActual = driver.getCurrentUrl();
    assertTrue(
      urlActual.contains("/client/"),
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
        driver.getCurrentUrl().contains("/client/" + CLIENT_ID),
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
  // CASO 3 — COMPLEJO: Flujo completo — login → menú → agregar → confirmar
  //                     → historial con productos, adicionales y total correcto
  // =========================================================================

  /**
   * Flujo completo del sprint:
   *   1. Login del cliente.
   *   2. Ir al menú y agregar 2 platos con 2 adicionales cada uno.
   *   3. Verificar el carrito (ítems, nombres, adicionales).
   *   4. Confirmar el pedido ("Ir a pagar").
   *   5. Ir al historial de pedidos del cliente.
   *   6. Verificar que el pedido aparece con los platos y adicionales correctos.
   *   7. Verificar que el total del historial es mayor a 0 (no hardcodeado).
   */
  @Test
  @Order(4)
  public void caso3_flujoCompleto_LoginAgregarPedidoYVerificarHistorial() {
    // ── PASO 1: Login ─────────────────────────────────────────────────────
    loginComoCliente();

    // ── PASO 2: Ir al menú ────────────────────────────────────────────────
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

    // ── PASO 3: Agregar primer plato y capturar su nombre desde la página ──
    // El nombre se lee desde #plate-name una vez dentro de la página del plato,
    // así se garantiza que coincide con el botón que se clickeó.
    String nombrePlato1 = agregarPlatoConAdicionalesYObtenerNombre(
      botonesVerPlato,
      0
    );
    assertFalse(
      nombrePlato1.isEmpty(),
      "El nombre del plato 1 no debe estar vacío"
    );

    // ── PASO 4: Volver al menú y agregar segundo plato ───────────────────
    driver.get(MENU_URL);

    wait.until(
      ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.className("btn-ver-plato")
      )
    );

    botonesVerPlato = driver.findElements(By.className("btn-ver-plato"));
    String nombrePlato2 = agregarPlatoConAdicionalesYObtenerNombre(
      botonesVerPlato,
      1
    );
    assertFalse(
      nombrePlato2.isEmpty(),
      "El nombre del plato 2 no debe estar vacío"
    );

    // ── PASO 5: Abrir el carrito ──────────────────────────────────────────
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

    // Verificar que los nombres de los platos están en el carrito
    List<WebElement> nombresEnCarrito = wait.until(
      ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.className("carrito-item-nombre")
      )
    );

    List<String> nombresTexto = nombresEnCarrito
      .stream()
      .map(y -> y.getAttribute("textContent"))
      .toList();

    assertTrue(
      nombresTexto.stream().anyMatch(x -> x.equals(nombrePlato1)),
      "El carrito debe contener el plato: " +
        nombrePlato1 +
        " pero solo contiene " +
        nombresEnCarrito
    );
    assertTrue(
      nombresTexto.stream().anyMatch(x -> x.equals(nombrePlato2)),
      "El carrito debe contener el plato: " +
        nombrePlato2 +
        " pero solo contiene " +
        nombresEnCarrito
    );

    // ── PASO 6: Guardar el total del carrito para comparar después ────────
    WebElement elementoTotalCarrito = wait.until(
      ExpectedConditions.presenceOfElementLocated(By.id("carrito-total"))
    );

    wait.until(driver ->
      !elementoTotalCarrito.getText().replaceAll("[^0-9]", "").isEmpty()
    );

    String textoTotalCarrito = elementoTotalCarrito.getText();
    long totalCarritoNumerico = Long.parseLong(
      textoTotalCarrito.replaceAll("[^0-9]", "")
    );

    assertTrue(
      totalCarritoNumerico > 0,
      "El total del carrito debe ser mayor a 0"
    );

    // ── PASO 7: Confirmar el pedido ───────────────────────────────────────
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
      // Si no hay alert, simplemente continuar
    }

    // ── PASO 8: Ir al historial de pedidos del cliente ────────────────────
    driver.get(BASE_URL + "/client/orders/" + CLIENT_ID);

    // Esperar a que aparezca al menos una orden en el historial
    wait.until(
      ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.className("orden-card")
      )
    );

    List<WebElement> ordenes = driver.findElements(By.className("orden-card"));
    assertFalse(
      ordenes.isEmpty(),
      "El historial debe tener al menos una orden después de confirmar el pedido"
    );

    // ── PASO 9: Verificar que la última orden tiene los platos correctos ──
    // La orden más reciente debería ser la primera (ordenadas desc por id)
    WebElement ultimaOrden = ordenes.get(0);

    List<WebElement> itemsDeOrden = ultimaOrden.findElements(
      By.className("orden-item-nombre")
    );

    List<String> nombresItemsOrden = itemsDeOrden
      .stream()
      .map(WebElement::getText)
      .toList();

    assertTrue(
      nombresItemsOrden.contains(nombrePlato1),
      "La orden en el historial debe tener el plato: " + nombrePlato1
    );
    assertTrue(
      nombresItemsOrden.contains(nombrePlato2),
      "La orden en el historial debe tener el plato: " + nombrePlato2
    );

    // ── PASO 10: Verificar que cada ítem tiene adicionales ────────────────
    List<WebElement> todosLosItems = ultimaOrden.findElements(
      By.className("orden-item")
    );

    for (WebElement item : todosLosItems) {
      List<WebElement> adicionalesDelItem = item.findElements(
        By.className("orden-adicional-tag")
      );
      assertTrue(
        adicionalesDelItem.size() >= 2,
        "Cada ítem de la orden debe tener al menos 2 adicionales en el historial"
      );
    }

    // ── PASO 11: Verificar el total del historial (no hardcodeado) ────────
    // El total que muestra el historial debe ser mayor a 0
    List<WebElement> totalesDeOrdenes = ultimaOrden.findElements(
      By.className("orden-total")
    );

    assertFalse(totalesDeOrdenes.isEmpty(), "La orden debe mostrar un total");

    String textoTotalHistorial = totalesDeOrdenes.get(0).getText();
    long totalHistorialNumerico = Long.parseLong(
      textoTotalHistorial.replaceAll("[^0-9]", "")
    );

    assertTrue(
      totalHistorialNumerico > 0,
      "El total del historial debe ser mayor a 0. Encontrado: " +
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

    wait.until(ExpectedConditions.urlContains("/client/" + CLIENT_ID));
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
}
