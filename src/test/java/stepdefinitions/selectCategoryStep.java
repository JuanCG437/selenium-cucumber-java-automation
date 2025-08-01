package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import utils.DriverManager;

public class selectCategoryStep {

    private final HomePage homePage;


    public selectCategoryStep() {
        WebDriver driver = DriverManager.getDriver();
        this.homePage = new HomePage(driver);

    }

    @When("the user click the category tab")
    public void userSelectCategory() throws Exception{
        homePage.selectCategory("Categorías");
    }

    @And("hovers over category {string}")
    public void userHoverCategory(String category) throws Exception{
        homePage.hoverOnTheCategory(category);
    }

    @And("select type product of category {string}")
    public void userSelectTypeProduct(String typeCategory) throws Exception{
        homePage.selectTypeCategory(typeCategory);
    }

    @Then("the selected type product {string} should be displayed in the location section")
    public void validateApplyCategory(String categoryTitleExpected) throws Exception{
        By element = By.xpath("//h1[contains(@class, 'ui-category-trends-header-title')]//b");
        homePage.assertTextIsContained(categoryTitleExpected, element);
    }
}
