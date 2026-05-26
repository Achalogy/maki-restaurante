package com.maki.web.e2e;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.maki.web.MakiApplication;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = MakiApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CorsTest {

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
    public void Cors_Allowed() {
        driver.get(BASE_URL);

        String backendUrl =
                "http://localhost:8080/api/v1/health"; // Cambia esto por un endpoint real de tu app

        String script =
                "return fetch('"
                        + backendUrl
                        + "')"
                        + "  .then(response => response.ok)"
                        + "  .catch(error => { console.error('CORS Error:', error); return false; });";

        Object result =
                ((JavascriptExecutor) driver)
                        .executeAsyncScript(
                                "var callback = arguments[arguments.length - 1];"
                                        + "fetch('"
                                        + backendUrl
                                        + "')"
                                        + "  .then(res => callback(res.status))"
                                        + "  .catch(err => callback('ERROR'));");

        assertTrue(
                result != null && !result.toString().equals("ERROR"),
                "La petición falló. Probablemente un error de CORS o el backend está caído.");
    }
}
