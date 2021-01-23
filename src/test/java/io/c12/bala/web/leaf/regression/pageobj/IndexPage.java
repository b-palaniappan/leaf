package io.c12.bala.web.leaf.regression.pageobj;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IndexPage {

    private final WebDriver driver;

    public IndexPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "header")
    private WebElement header;

    public void openLoginPage() {
        this.driver.get("http://localhost:8080");
    }

    public String getHeaderMessage() {
        return header.getText();
    }
}
