package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    @LocalServerPort
    private int port;
    private WebDriver driver;
    private String firstName = "sarah";
    private String lastName = "alshahrani";
    private String userName = "sarah";
    private String password = "12345";

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testUserAccess() {
        driver.get("http://localhost:" + this.port + "/login");
        assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/signup");
        assertEquals("Sign Up", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        assertEquals("Login", driver.getTitle());
    }

    @Test
    public void testSignUp() {
        driver.get("http://localhost:" + this.port + "/signup");
        assertEquals("Sign Up", driver.getTitle());

        driver.findElement(By.id("inputFirstName")).sendKeys(firstName);
        driver.findElement(By.id("inputLastName")).sendKeys(lastName);
        driver.findElement(By.id("inputUsername")).sendKeys(userName);
        driver.findElement(By.id("inputPassword")).sendKeys(password);
        driver.findElement(By.id("buttonSignUp")).click();
    }

    @Test
    public void testLoginLogout() {
        driver.get("http://localhost:" + this.port + "/login");
        assertEquals("Login", driver.getTitle());

        driver.findElement(By.id("inputUsername")).sendKeys(userName);
        driver.findElement(By.id("inputPassword")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        assertEquals("Home", driver.getTitle());

        WebDriverWait wait = new WebDriverWait(driver, 20);

        driver.findElement(By.id("logoutButton")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
        assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals("Login", driver.getTitle());
    }

}

