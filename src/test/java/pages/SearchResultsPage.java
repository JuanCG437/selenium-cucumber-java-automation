package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LogHelper;

import java.time.Duration;

import java.util.List;
import java.util.logging.Logger;

public class SearchResultsPage extends BasePage{

    private static final Logger LOGGER = LogHelper.getLogger(SearchResultsPage.class);

    public SearchResultsPage(WebDriver driver){
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectTypeFilter(String value) throws Exception{
        String xpath = String.format("//a[contains(@aria-label, '%s')]", value);
        click(By.xpath(xpath));
    }

    public boolean specificResultsContainText(String expectedText, int maxResultsToCheck) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a.poly-component__title")));

            By productTitleSelector = By.cssSelector("a.poly-component__title");
            List<WebElement> productTitles = driver.findElements(By.cssSelector("a.poly-component__title"));

            int checked = 0;
            for (int i = 0; i < productTitles.size(); i++) {
                WebElement title = driver.findElements(productTitleSelector).get(i);

                String productText = title.getText().toLowerCase();

                if (!productText.contains(expectedText.toLowerCase())) {
                    LOGGER.info("No contains: " +expectedText + " => " + productText);
                    return false;
                }

                checked++;
                if (checked >= maxResultsToCheck) break;
            }
            return true;
        } catch (StaleElementReferenceException staleEx) {
            LOGGER.severe("StaleElementReferenceException: DOM change. try again.");
            return specificResultsContainText(expectedText, maxResultsToCheck);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
