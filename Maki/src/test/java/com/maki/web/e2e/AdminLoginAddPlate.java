package com.maki.web.e2e;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.maki.web.MakiApplication;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminLoginAddPlate {

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
    options.addArguments("--headless");
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

  @AfterEach
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }
}
