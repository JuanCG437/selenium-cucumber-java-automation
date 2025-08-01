package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import models.TestData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.HomePage;

import utils.DriverManager;
import utils.JsonReader;

public class SelectShippingAddressStep {

    private final WebDriver driver = DriverManager.getDriver();
    private final TestData testData;
    private final HomePage homePage = new HomePage(driver);

    public SelectShippingAddressStep(){
        this.testData = JsonReader.getData();
    }

    @Given("user on the home page")
    public void userAccessHomeAmazonPage() {
        driver.get(testData.getUrl());
    }

    @When("user click on the enter location section")
    public void userSearchProduct() throws Exception{
        homePage.clickShippingAddress();
    }

    @And("select state {string}")
    public void userSelectState(String state) throws Exception{
        homePage.switchToAddressIframe("iframe[src*='addresses-hub']");
        homePage.selectDepartment(state);
        homePage.waitForCityDropdownToEnabled();
    }

    @And("select a city {string} located in the selected state")
    public void userSelectCity(String city) throws Exception{
        homePage.waitForCityDropdownToEnabled();
        homePage.selectCity(city);
    }

    @And("click on the accept button")
    public void userClickAcceptButton() throws Exception{
        homePage.clickAcceptButton();
        DriverManager.getDriver().switchTo().defaultContent();
    }

    @Then("the selected city {string} should be displayed in the location section")
    public void validateFilterBrand(String expectedCity) throws  Exception{
        By element = By.xpath("//a[contains(@class, 'nav-menu-cp') and contains(@class, 'nav-menu-cp-logged')]");
        homePage.assertTextIsContained(expectedCity, element);
    }
}
