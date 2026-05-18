package com.maki.web.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.maki.web.MakiApplication;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
  classes = MakiApplication.class
)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class AdminLoginAddPlate {
 // Configuración común para todas las pruebas
  private WebDriver driver;
  private WebDriverWait wait;
  private static final String BASE_URL = "http://localhost:4200";
  private static final int WAIT_SECONDS = 10;

  @BeforeEach
  public void setUp() {
    // Configura automáticamente el driver de Chrome
    WebDriverManager.chromedriver().setup();

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--disable-extensions");
    options.addArguments("--disable-notifications");
    
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--disable-gpu");
    options.addArguments("--window-size=1920,1080");

    this.driver = new ChromeDriver(options);
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));

    // Limpiar el localStorage antes de cada prueba para que no haya
    // sesión ni carrito residual de pruebas anteriores
    driver.get(BASE_URL);
    ((JavascriptExecutor) driver).executeScript("localStorage.clear();");
  }
  // Método privado reutilizable
  private void doLoginAndGoToPlatePanel() {
    driver.get(BASE_URL);

    WebElement loginButton = wait.until(
        ExpectedConditions.elementToBeClickable(By.className("header-profile"))
    );
    loginButton.click();
    wait.until(ExpectedConditions.urlContains("/client/log-in"));

    WebElement adminLink = wait.until(
        ExpectedConditions.elementToBeClickable(By.id("LogAdmin"))
    );
    adminLink.click();
    wait.until(ExpectedConditions.urlContains("/admin/log-in"));

    WebElement usernameField = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("username"))
    );
    usernameField.sendKeys("Neon");

    WebElement passwordField = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("password"))
    );
    passwordField.sendKeys("4567");

    WebElement loginAdButton = wait.until(
        ExpectedConditions.elementToBeClickable(By.id("logInAdButton"))
    );
    loginAdButton.click();

    wait.until(ExpectedConditions.urlToBe(BASE_URL + "/admin"));

    WebElement platosButton = wait.until(
        ExpectedConditions.elementToBeClickable(By.id("gateway-platos"))
    );
    platosButton.click();
    wait.until(ExpectedConditions.urlContains("/plate/crud"));
  }

  @Test
  public void Admin_Login_Wrong_Credentials() {
    //Inicia en landing page
    driver.get(BASE_URL);

    //Presiona el botón de iniciar sesión
    WebElement loginButton = wait.until(
      ExpectedConditions.elementToBeClickable(By.className("header-profile"))
    );
    loginButton.click();

    wait.until(ExpectedConditions.urlContains("/client/log-in"));

    //¿Llego a login de cliente?
    assertTrue(
      driver.getCurrentUrl().contains("/client/log-in"),
      "No navegó al login de cliente"
    );

    //Va a a iniciar sesion como administrador
    WebElement adminLink = wait.until(
      ExpectedConditions.elementToBeClickable(By.id("LogAdmin"))
    );
    adminLink.click();

    wait.until(ExpectedConditions.urlContains("/admin/log-in"));

    //¿Llego a login de Admin?
    assertTrue(
      driver.getCurrentUrl().contains("/admin/log-in"),
      "No navegó al login de administrador"
    );

    //¿El formulario de login de administrador está presente?
    WebElement usernameField = wait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("username"))
    );
    assertTrue(
      usernameField.isDisplayed(),
      "El campo de usuario no es visible"
    );

    //Ingresa credenciales incorrectas
    usernameField.sendKeys("wrongAdmin");

    //Contraseña
    WebElement passwordField = wait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("password"))
    );
    passwordField.sendKeys("wrongPassword");

    //Da click en iniciar sesión
    WebElement loginAdButton = wait.until(
      ExpectedConditions.elementToBeClickable(By.id("logInAdButton"))
    );

    loginAdButton.click();
    wait.until(ExpectedConditions.urlContains("/admin/log-in"));

    //¿Sigue en el login de Admin?
    assertTrue(
      driver.getCurrentUrl().contains("/admin/log-in"),
      "No debio cambiar de pantalla"
    );

    //¿Aparece mensaje de error?
    WebElement errorMessage = wait.until(
      ExpectedConditions.visibilityOfElementLocated(
        By.id("invalid-credentials-error")
      )
    );
    assertTrue(
      errorMessage.isDisplayed(),
      "No aparece mensaje de error correspondiente"
    );
  }

  @Test
  public void Admin_Login_Correct_Go_To_PlatePanel() {
    //Igual al anterior, pero ahora con un usuario existente, como ya se comprobaron algunos elementos, se descarta volverlos a probar
    //Inicia en landing page
    driver.get(BASE_URL);

    //Presiona el botón de iniciar sesión
    WebElement loginButton = wait.until(
      ExpectedConditions.elementToBeClickable(By.className("header-profile"))
    );
    loginButton.click();

    wait.until(ExpectedConditions.urlContains("/client/log-in"));

    //Va a a iniciar sesion como administrador
    WebElement adminLink = wait.until(
      ExpectedConditions.elementToBeClickable(By.id("LogAdmin"))
    );
    adminLink.click();

    wait.until(ExpectedConditions.urlContains("/admin/log-in"));

    //Ingresa credenciales correctas
    //Usuario: Neon
    WebElement usernameField = wait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("username"))
    );
    usernameField.sendKeys("Neon");

    //Contraseña: 4567
    WebElement passwordField = wait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("password"))
    );
    passwordField.sendKeys("4567");

    //Da click en iniciar sesión
    WebElement loginAdButton = wait.until(
      ExpectedConditions.elementToBeClickable(By.id("logInAdButton"))
    );

    loginAdButton.click();

    //Debe llegar a la pantalla de administración
    wait.until(ExpectedConditions.urlToBe(BASE_URL + "/admin"));
    assertTrue(
      driver.getCurrentUrl().equals(BASE_URL + "/admin"),
      "No navegó a la pantalla de administración"
    );

    //Busca y va a administrar platos
    WebElement platosButton = wait.until(
      ExpectedConditions.elementToBeClickable(By.id("gateway-platos"))
    );
    platosButton.click();
    wait.until(ExpectedConditions.urlContains("/plate/crud"));

    //¿Llego a la pantalla de administración de platos?
    assertTrue(
      driver.getCurrentUrl().contains("/plate/crud"),
      "No navegó a la pantalla de administración de platos"
    );
  }
  @Test
  public void Admin_Go_Products_Create_New_Product_2_Aditionals() {
    doLoginAndGoToPlatePanel();

    //Da click en crear nuevo plato
    WebElement createPlateButton = wait.until(
      ExpectedConditions.elementToBeClickable(By.id("createPlateButton"))
    );
    createPlateButton.click();
    wait.until(ExpectedConditions.urlContains("/plate/create"));

    //¿Llego a la pantalla de creación de plato?
    assertTrue(
      driver.getCurrentUrl().contains("/plate/create"),
      "No navegó a la pantalla de creación de plato"
    );

    //Llena el formulario con datos validos
    WebElement nameField = wait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("name"))
    );
    nameField.sendKeys("Natto");

    //Agrega la descripcion
    WebElement description = wait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("description"))
    );
    description.sendKeys("Un plato bien picho de frijoles pegajosos");

    //Agrega el precio
    WebElement price = wait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("price"))
    );
    price.sendKeys("25000");

    //Agrega la url de la imagen
    WebElement urlImage = wait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("urlImage"))
    );
    urlImage.sendKeys("https://ychef.files.bbci.co.uk/1280x720/p08ksq68.jpg");

    //Selecciona la categoria
    WebElement categoryDropdown = wait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("categoryId"))
    );

    //Esperar a que el dropdown se llene con las categorias
    wait.until(driver -> {
      Select sel = new Select(driver.findElement(By.id("categoryId")));
      return sel.getOptions().size() > 1;
    });

    Select categorySelect = new Select(categoryDropdown);
    String categoryOption = selectFirstMatchingOption(categorySelect, "Platos fuertes");
    categorySelect.selectByVisibleText(categoryOption);

    //Da click en guardar
    WebElement saveButton = wait.until( 
      ExpectedConditions.elementToBeClickable(By.id("savePlateButton"))
    );
    saveButton.click();

        // Abrir nueva pestaña con el menú
    ((JavascriptExecutor) driver).executeScript("window.open('" + BASE_URL + "/plate/menu', '_blank');");

    // Obtener todos los handles de ventanas/pestañas abiertas
    // El Set mantiene el orden de apertura, así que el último es la nueva pestaña
    java.util.Set<String> handles = driver.getWindowHandles();
    String newTabHandle = handles.stream()
        .reduce((first, second) -> second)  // toma el último
        .orElseThrow();

    // Cambiar el foco a la nueva pestaña
    driver.switchTo().window(newTabHandle);

    // Verificar que estamos en la URL correcta
    wait.until(ExpectedConditions.urlContains("/plate/menu"));
    assertTrue(
        driver.getCurrentUrl().contains("/plate/menu"),
        "La nueva pestaña debe mostrar el menú"
    );
    // Esperar a que las plate-cards carguen en el menú
    wait.until(
        ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("plate-card"))
    );
    // Buscar la card cuyo plate-card-name sea exactamente "Natto"
    WebElement nattoCard = wait.until(driver -> {
        java.util.List<WebElement> cards = driver.findElements(By.className("plate-card"));
        return cards.stream()
            .filter(card -> {
                java.util.List<WebElement> nombres = card.findElements(By.className("plate-card-name"));
                return !nombres.isEmpty() && nombres.get(0).getText().equals("Natto");
            })
            .findFirst()
            .orElse(null);  // retorna null si no la encuentra aún → el wait reintenta
    });
    assertNotNull(nattoCard, "No se encontró la card del plato 'Natto' en el menú");

    // Dentro de esa card, hacer clic en su botón btn-ver-plato
    WebElement btnVerNatto = nattoCard.findElement(By.className("btn-ver-plato"));
    btnVerNatto.click();

    // Verificar que navegó a la página del plato
    wait.until(ExpectedConditions.urlContains("/plate/"));
    assertTrue(
        driver.getCurrentUrl().contains("/plate/"),
        "Debe navegar al detalle del plato Natto"
    );
  }

  @Test
  public void Admin_add_new_additional_to_category(){
    doLoginAndGoToPlatePanel();

    //ir al menu
    WebElement menuLinkd = wait.until( 
      ExpectedConditions.elementToBeClickable(By.id("menu-link"))
    );
    menuLinkd.click();
    wait.until(ExpectedConditions.urlContains("/plate/menu"));

    // Abrir el detalle de un plato del menú para leer los adicionales
    openPlateDetailFromMenu("Gyoza");

    // Contar los adicionales disponibles
    List<WebElement> adicionales = driver.findElements(By.className("additional-checkbox"));

    if (adicionales.size() == 6) {
        // Si tiene 6 adicionales, volver al menú
        driver.navigate().back();

        // Verificar que volvió al menú
        wait.until(ExpectedConditions.urlContains("/plate/menu"));
        assertTrue(
            driver.getCurrentUrl().contains("/plate/menu"),
            "Debe haber vuelto al menú al encontrar 6 adicionales"
        );
    }

    // Ir a la pagina del gateway del administrador
    WebElement adminButton = wait.until( 
      ExpectedConditions.elementToBeClickable(By.id("profile-link"))
    );
    adminButton.click();

    // Ir al menu the adicionales
    WebElement adicionalesButton = wait.until( 
      ExpectedConditions.elementToBeClickable(By.id("gateway-adicionales"))
    );
    adicionalesButton.click();
    wait.until(ExpectedConditions.urlContains("/additional/crud"));

    // Da click en crea un nuevo adicional
    WebElement createAdditional = wait.until( 
      ExpectedConditions.elementToBeClickable(By.id("createAdditionalButton"))
    );
    createAdditional.click();
    wait.until(ExpectedConditions.urlContains("/additional/create"));

    // Llenar el nombre del adicional
    WebElement nameField = wait.until( 
      ExpectedConditions.visibilityOfElementLocated(By.id("name"))
    );
    nameField.sendKeys("Aji");

    // Llena el precio del adicional
    WebElement priceField = wait.until( 
      ExpectedConditions.visibilityOfElementLocated(By.id("price"))
    );
    priceField.sendKeys("2000");

    // Esperar a que el dropdown de categorías se llene
    WebElement categoriesDropdown = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("categories"))
    );

    wait.until(driver -> {
        Select sel = new Select(driver.findElement(By.id("categories")));
        return sel.getOptions().size() > 0;
    });

    Select categoriesSelect = new Select(categoriesDropdown);

    if (categoriesSelect.isMultiple()) {
      categoriesSelect.deselectAll();
    }

    String additionalCategory = selectFirstMatchingOption(categoriesSelect, "Platos fuertes");
    categoriesSelect.selectByVisibleText(additionalCategory);

    // Verificar que solo hay UNA opción seleccionada
    List<WebElement> seleccionadas = categoriesSelect.getAllSelectedOptions();
    assertEquals(
        1,
        seleccionadas.size(),
        "Solo debe haber una categoría seleccionada"
    );
    assertEquals(
        additionalCategory,
        seleccionadas.get(0).getText().trim(),
        "La categoría seleccionada debe coincidir con la opción elegida"
    );

    // Guardar el adicional
    WebElement saveButton = wait.until(
        ExpectedConditions.elementToBeClickable(By.id("saveButton"))
    );
    saveButton.click();

    // Verificar que el adicional aparece en el listado de adicionales
    driver.navigate().to(BASE_URL + "/additional/crud");
    wait.until(ExpectedConditions.urlContains("/additional/crud"));
    wait.until(ExpectedConditions.presenceOfElementLocated(
        By.xpath("//*[normalize-space(text())='Aji']")
    ));

    assertTrue(
        driver.findElements(By.xpath("//*[normalize-space(text())='Aji']")).size() > 0,
        "El adicional 'Aji' debe aparecer en el listado de adicionales"
    );
  }

  private String selectFirstMatchingOption(Select select, String desiredText) {
    return select.getOptions().stream()
        .map(WebElement::getText)
        .filter(text -> text != null && !text.trim().isEmpty())
        .map(String::trim)
        .filter(text -> text.equalsIgnoreCase(desiredText))
        .findFirst()
        .orElseGet(() ->
            select.getOptions().stream()
                .map(WebElement::getText)
                .filter(text -> text != null && !text.trim().isEmpty())
                .map(String::trim)
                .filter(text -> !text.toLowerCase().contains("select"))
                .findFirst()
                .orElseThrow(() ->
                    new AssertionError("No category option available for: " + desiredText))
        );
  }

  private void openPlateDetailFromMenu(String plateName) {
    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("plate-card")));
    List<WebElement> plateCards = driver.findElements(By.className("plate-card"));
    assertTrue(!plateCards.isEmpty(), "No hay platos disponibles en el menú");

    WebElement targetCard = plateCards.stream()
        .filter(card -> {
            List<WebElement> nombres = card.findElements(By.className("plate-card-name"));
            return !nombres.isEmpty() && nombres.get(0).getText().trim().equalsIgnoreCase(plateName);
        })
        .findFirst()
        .orElseGet(() -> plateCards.get(0));

    WebElement btnVerPlato = targetCard.findElement(By.className("btn-ver-plato"));
    wait.until(ExpectedConditions.elementToBeClickable(btnVerPlato)).click();
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("plate-name")));
  }

  @AfterEach
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }
}