package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialTest {
    @LocalServerPort
    private int port;
    private WebDriver driver;
    private String userName = "sarah";
    private String password = "12345";
    private String credentialURL = "https://www.google.com/";
    String newUsername = "sarah1";

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
    @Order(value = 1)
    public void createCredentialTest() {
        WebDriverWait wait = new WebDriverWait (driver, 20);
        JavascriptExecutor jse =(JavascriptExecutor) driver;
        login();

        WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialsTab);
        wait.withTimeout(Duration.ofSeconds(20));
        WebElement newCredentialButton = driver.findElement(By.id("new-credential-button"));
        wait.until(ExpectedConditions.elementToBeClickable(newCredentialButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credentialURL);
        WebElement credentialUsernameInput = driver.findElement(By.id("credential-username"));
        credentialUsernameInput.sendKeys(userName);
        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        credentialPassword.sendKeys(password);

        WebElement submit = driver.findElement(By.id("save-credential"));
        submit.click();
        Assertions.assertEquals("Result", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialsTab);
        WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> credentialsList = credentialsTable.findElements(By.tagName("td"));
        Boolean created = false;
        for (WebElement element : credentialsList) {
            if (element.getAttribute("innerHTML").equals(userName)) {
                created = true;
                break;
            }
        }

        Assertions.assertTrue(created);
    }

    @Test
    @Order(value = 2)
    public void editCredentialTest(){
        WebDriverWait wait = new WebDriverWait (driver, 20);
        JavascriptExecutor jse =(JavascriptExecutor) driver;
        login();

        WebElement credentialsUsernameInput = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialsUsernameInput);
        WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement>credentialsList = credentialsTable.findElements(By.tagName("td"));
        WebElement editElement = null;
        for (WebElement element : credentialsList) {
            editElement = element.findElement(By.id("edit-credential"));
            if (editElement != null) {
                break;
            }
        }

        wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
        credentialsUsernameInput = driver.findElement(By.id("credential-username"));
        wait.until(ExpectedConditions.elementToBeClickable(credentialsUsernameInput));
        credentialsUsernameInput.clear();
        credentialsUsernameInput.sendKeys(newUsername);
        WebElement saveChanges = driver.findElement(By.id("save-credential"));
        saveChanges.click();
        Assertions.assertEquals("Result", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals(userName, newUsername);
    }

    @Test
    @Order(value = 3)
    public void deleteCredentialTest(){
        WebDriverWait wait = new WebDriverWait (driver, 20);
        JavascriptExecutor jse =(JavascriptExecutor) driver;
        login();

        WebElement credentialsUsernameInput = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialsUsernameInput);
        WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
        List <WebElement> credentialsList = credentialsTable.findElements(By.tagName("td"));
        WebElement deleteElement = null;
        for (WebElement element : credentialsList) {
            deleteElement = element.findElement(By.id("delete-credential"));
            if (deleteElement != null) {
                break;
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
        Assertions.assertEquals("Result", driver.getTitle());
    }

    private void login(){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());

        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.sendKeys(userName);
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.sendKeys(password);
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", driver.getTitle());
    }
}
