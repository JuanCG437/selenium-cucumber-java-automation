package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LogHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.logging.Logger;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private static final int TIMEOUT = 15;
    private static final Logger LOGGER = LogHelper.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    protected WebElement waitForVisibility(By locator) throws Exception {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (NoSuchElementException | TimeoutException e) {
            captureScreenshot("failure_" + System.currentTimeMillis());
            LOGGER.severe("Timeout waiting for clickability of: " + locator + " . Error: " + e.getMessage());
            throw new Exception("Timeout waiting for clickability of: " + locator, e);
        }
    }

    protected WebElement waitForClickability(By locator) throws Exception {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (NoSuchElementException | TimeoutException e) {
            captureScreenshot("failure_" + System.currentTimeMillis());
            LOGGER.severe("Timeout waiting for clickability of: " + locator + " . Error: " + e.getMessage());
            throw new Exception("Timeout waiting for clickability of: " + locator, e);
        }
    }

    public void click(By locator) throws Exception {
        try {
            waitForClickability(locator).click();
            LOGGER.info("Clicked on element: " + locator);
            captureScreenshot("failure_" + System.currentTimeMillis());
        } catch (NoSuchElementException | TimeoutException e) {
            captureScreenshot("failure_" + System.currentTimeMillis());
            LOGGER.severe("Element not found: " + locator + " . Error: " + e.getMessage());
            throw new Exception("Element not found: " + locator + ". Detail " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.severe("Fail click on: " + locator + " . Error: " + e.getMessage());
            throw new Exception("Fail click on: " + locator + ". Detail: " + e.getMessage(), e);
        }
    }

    public void clickByJS(By locator) throws Exception {
        try {
            WebElement element = waitForClickability(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            LOGGER.info("Clicked via JS on element " + locator);
        } catch (Exception e) {
            LOGGER.severe("Failed JS click to element: " + locator + ".Error " + e.getMessage());
            throw new Exception("The element " + locator + "is not visible in the DOM");
        }
    }

    public String getText(By locator) throws Exception {
        try {
            String text = driver.findElement(locator).getText();
            LOGGER.info("Text from element " + locator + ": " + text);
            return text;
        } catch (NoSuchElementException | TimeoutException e) {
            captureScreenshot("failure_" + System.currentTimeMillis());
            LOGGER.severe("Element not found: " + locator + " . Error: " + e.getMessage());
            throw new Exception("Element not found: " + locator + " . Error: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.severe("Cannot get text from: " + locator + ".Error: " + e.getMessage());
            throw new Exception("Cannot get text from: " + locator + ". Details: " + e.getMessage(), e);
        }
    }

    public void writeText(By locator, String text) throws Exception {
        try {
            WebElement element = waitForVisibility(locator);
            element.clear();
            element.sendKeys(text);

            /*/new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.attributeToBe(locator, "value", text));*/

            element.sendKeys(Keys.TAB);
            LOGGER.info("Wrote text in element " + locator + ": \"" + text + "\"");
        } catch (NoSuchElementException | TimeoutException e) {
            captureScreenshot("failure_" + System.currentTimeMillis());
            LOGGER.severe("Element not found: " + locator + " . Error: " + e.getMessage());
            throw new Exception("Element not found: " + locator + ". Error: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.severe("Cannot write text to element: " + locator + " . Error: " + e.getMessage());
            throw new Exception("Cannot write text to element " + locator + " . Details: " + e.getMessage(), e);
        }
    }

    public void hoverOverElement(By locator) throws Exception {
        try {
            WebElement element = waitForVisibility(locator);
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
            LOGGER.info("Hovered over element: " + locator);
        } catch (NoSuchElementException | TimeoutException e) {
            captureScreenshot("failure_" + System.currentTimeMillis());
            LOGGER.severe("Element not found: " + locator + " . Error: " + e.getMessage());
            throw new Exception("Element not found: " + locator + " . Details: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.severe("Failed to hover element: " + locator + " . Error: " + e.getMessage());
            throw new Exception("Failed to hover element: " + locator + " . Details: " + e.getMessage(), e);
        }
    }

    public void captureScreenshot(String fileName) {
        try {
            Path screenshotsDir = Path.of("build/screenshots/");
            Files.createDirectories(screenshotsDir);

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), screenshotsDir.resolve(fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
