package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utils.LogHelper;

import java.util.List;
import java.util.logging.Logger;

public class HomePage extends BasePage{

    private static final Logger LOGGER = LogHelper.getLogger(HomePage.class);

    private final By searchBar = By.id("cb1-edit");
    private final By searchBarButton = By.xpath("//button[@type='submit' and contains(@class, 'nav-search-btn')]");
    private final By departmentDropDown = By.xpath("//select[@data-testid='stateDropdown' and contains(@class, 'andes-form-control__field')]");
    private final By cityDropdown = By.xpath("//select[@data-testid='citiesDropdown' and contains(@class, 'andes-form-control__field')]");

    public HomePage(WebDriver driver){
        super(driver);
    }

    public void searchProduct(String text) throws Exception{
        this.writeText(searchBar, text);
        this.click(searchBarButton);
    }

    public boolean resultContainsProduct(int minimumExpected) throws Exception{
        try {
            By resultLocator = By.xpath("//div[@class='poly-card__content']//h3[@class='poly-component__title-wrapper']/a");

            wait.until(ExpectedConditions.presenceOfElementLocated(resultLocator));

            List<WebElement> results = driver.findElements(By.cssSelector("div.poly-card.poly-card--CORE"));
            LOGGER.info(() -> "results found: " + results.size());

            return results.size() >= minimumExpected;

        } catch (TimeoutException e) {
            throw new Exception("Timeout waiting search results: " + e.getMessage());
        }
    }

    public void clickShippingAddress() throws Exception{
        By shippingAddressElement = By.xpath("//a[contains(@class, 'nav-menu-cp') and contains(@class, 'nav-menu-cp-logged')]");
        this.click(shippingAddressElement);
    }

    public void selectDepartment(String value) throws Exception{
        try {
            WebElement dropdown = waitForVisibility(departmentDropDown);
            Select select = new Select(dropdown);
            select.selectByVisibleText(value);
            Thread.sleep(300);
            String script = "var select = arguments[0];" +
                    "select.dispatchEvent(new Event('input', { bubbles: true }));" +
                    "select.dispatchEvent(new Event('change', { bubbles: true }));";
            ((JavascriptExecutor) driver).executeScript(script, dropdown);

            LOGGER.info("Selected department with JS: " + value);
        } catch (Exception e) {
            LOGGER.severe("Failed to select department '" + value + "'. Error: " + e.getMessage());
            throw new Exception("Couldn't select department: " + value + " . " + e.getMessage());
        }
    }

    public void waitForCityDropdownToEnabled() throws Exception{
        try {
            wait.until(driver1 -> {
                WebElement dropdown = null;
                try {
                    dropdown = waitForClickability(cityDropdown);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                boolean isVisible = dropdown.isDisplayed() && dropdown.isEnabled();
                boolean hasOptions = new Select(dropdown).getOptions().size() > 1;
                return isVisible && hasOptions;
            });
            LOGGER.info("City dropdown is enable and has options");
        } catch (Exception e) {
            LOGGER.severe("City dropdown did not become enabled: " + e.getMessage());
            throw new Exception("City dropdown not enabled.");
        }
    }

    public void selectCity(String value) throws Exception{
        try {
            WebElement dropdown = waitForVisibility(cityDropdown);
            Select select = new Select(dropdown);
            select.selectByVisibleText(value);
            LOGGER.info("Selected city: " + value);
        } catch (Exception e) {
            LOGGER.severe("Failed to select city '" + value + "' . Error: " + e.getMessage());
            throw new Exception("Couldn't select city: " + value + " . " + e.getMessage());
        }
    }

    public void switchToAddressIframe(String iframeCssSelector) throws Exception{
        try {
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(iframeCssSelector)));
            driver.switchTo().frame(iframe);
            LOGGER.info("Changed iframe context");
        } catch (Exception e) {
            LOGGER.severe("Cannot changed iframe: " + e.getMessage());
            throw new Exception("Cannot changed iframe context");
        }
    }

    public void clickAcceptButton() throws Exception{
        this.click(By.id(":R23k9ke:"));
    }

    public void assertTextIsContained(String expectedText, By element) throws Exception{
        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(element, expectedText));
            LOGGER.info("Verified that element contains the text : \"" + expectedText + "\"");
        } catch (TimeoutException e) {
            String actualText = getText(element);
            throw new AssertionError("Expected text \"" + expectedText + "\" not found in element. Actual text \"" + actualText + "\"");
        }
    }

    public void selectCategory(String value) throws Exception{
        String xpath = String.format("//a[text()='%s']", value);
        click(By.xpath(xpath));
    }

    public void hoverOnTheCategory(String value) throws Exception{
        String xpath = String.format("//a[@data-order='0' and text()='%s']", value);
        hoverOverElement(By.xpath(xpath));
    }

    public void selectTypeCategory(String value) throws Exception{
        String xpath = String.format("//ul[@class='nav-categs-detail__categ-list']//a[contains(text(),'%s')]", value);
        click(By.xpath(xpath));
    }
}
