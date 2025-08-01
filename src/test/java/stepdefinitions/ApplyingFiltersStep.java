package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.SearchResultsPage;
import utils.DriverManager;

public class ApplyingFiltersStep {

    private final SearchResultsPage resultsPage;

    public ApplyingFiltersStep() {
        WebDriver driver = DriverManager.getDriver();
        this.resultsPage = new SearchResultsPage(driver);
    }

    @And("select the brand {string} of the product filter")
    public void userSelectTypeFilter(String brand) throws Exception{
        resultsPage.selectTypeFilter(brand);
    }

    @Then("the first {int} results should contain the brand {string}")
    public void resultsShouldContainBrand(int limit, String expectedBrand) {
        Assert.assertTrue("Some results do not contain the expected brand: " + expectedBrand,
                resultsPage.specificResultsContainText(expectedBrand, limit));
    }
}
