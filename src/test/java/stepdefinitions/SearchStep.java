package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.TestData;
import org.junit.Assert;
import pages.HomePage;
import utils.DriverManager;
import utils.JsonReader;

public class SearchStep {

    private final HomePage homePage;
    private final TestData testData;

    public SearchStep() {
        this.homePage = new HomePage(DriverManager.getDriver());
        this.testData = JsonReader.getData();
    }

    @Given("the user on the home page")
    public void userAccessHomePage() {
        String url = testData.getUrl();
        DriverManager.getDriver().get(url);
    }

    @When("search product")
    public void userSearchProduct() throws Exception{
        String product = testData.getProduct();
        homePage.searchProduct(product);
    }

    @Then("the user views a list of products related to the search")
    public void userViewsResults() throws Exception{
        Assert.assertTrue(homePage.resultContainsProduct(3));
    }
}
