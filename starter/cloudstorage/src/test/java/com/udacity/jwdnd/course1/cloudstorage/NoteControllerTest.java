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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteControllerTest {

    @LocalServerPort
    private int port;
    private WebDriver driver;
    private String userName = "sarah";
    private String password = "12345";
    private String noteTitle = "2024 Goals";
    private String noteDescription = "This note contain all my 2024 goals";
    private String newNoteDescription = "All my 2024 goals will be completed by the end of the year";

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
    public void createNoteTest() {
        WebDriverWait wait = new WebDriverWait (driver, 20);
        JavascriptExecutor jse =(JavascriptExecutor) driver;
        login();

        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(20));
        WebElement newNoteButton = driver.findElement(By.id("new-note-button"));
        wait.until(ExpectedConditions.elementToBeClickable(newNoteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
        WebElement noteDescriptionInput = driver.findElement(By.id("note-description"));
        noteDescriptionInput.sendKeys(noteDescription);

        WebElement saveChanges = driver.findElement(By.id("save-changes"));
        saveChanges.click();
        assertEquals("Result", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> notes = notesTable.findElements(By.tagName("td"));
        Boolean added = false;
        for (WebElement element : notes) {
            if (element.getAttribute("innerHTML").equals(noteDescription)) {
                added = true;
                break;
            }
        }

        Assertions.assertTrue(added);
    }

    @Test
    @Order(value = 2)
    public void editNoteTest(){
        WebDriverWait wait = new WebDriverWait (driver, 20);
        JavascriptExecutor jse =(JavascriptExecutor) driver;
        login();

        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> notes = notesTable.findElements(By.tagName("td"));
        WebElement editElement = null;
        for (WebElement element : notes) {
            editElement = element.findElement(By.id("edit-button"));
            if (editElement != null) {
                break;
            }
        }

        wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
        WebElement noteDescriptionInput = driver.findElement(By.id("note-description"));
        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionInput));
        String noteOldDescription = noteDescriptionInput.getAttribute("innerHTML");
        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(newNoteDescription);

        WebElement saveChanges = driver.findElement(By.id("save-changes"));
        saveChanges.click();
        assertEquals("Result", driver.getTitle());
        Assertions.assertNotEquals(noteOldDescription, newNoteDescription);
    }

    @Test
    @Order(value = 3)
    public void deleteNoteTest(){
        WebDriverWait wait = new WebDriverWait (driver, 20);
        JavascriptExecutor jse =(JavascriptExecutor) driver;
        login();

        driver.get("http://localhost:" + this.port + "/home");
        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> notes = notesTable.findElements(By.tagName("td"));
        WebElement deleteElement = null;
        for (WebElement element : notes) {
            deleteElement = element.findElement(By.id("delete-button"));
            if (deleteElement != null) {
                break;
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
        assertEquals("Result", driver.getTitle());
    }

    private void login(){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/login");
        assertEquals("Login", driver.getTitle());

        driver.findElement(By.id("inputUsername")).sendKeys(userName);
        driver.findElement(By.id("inputPassword")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        assertEquals("Home", driver.getTitle());
    }
}
