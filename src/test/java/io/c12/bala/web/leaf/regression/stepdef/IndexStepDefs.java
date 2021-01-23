package io.c12.bala.web.leaf.regression.stepdef;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import io.c12.bala.web.leaf.regression.pageobj.IndexPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class IndexStepDefs {

    private final IndexPage indexPage;

    private WebDriver driver;

    public IndexStepDefs() {
        driver = new HtmlUnitDriver(BrowserVersion.FIREFOX, true);
        driver.manage().window().maximize();
        indexPage = new IndexPage(driver);
    }

    @Given("I am in index page")
    public void i_am_in_index_page() {
        indexPage.openLoginPage();
    }

    @Then("I see {string} message")
    public void i_see_message(String message) {
        Assert.assertEquals(message, indexPage.getHeaderMessage());
        driver.quit();
    }
}
