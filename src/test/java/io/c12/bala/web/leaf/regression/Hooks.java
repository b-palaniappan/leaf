package io.c12.bala.web.leaf.regression;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import io.cucumber.java.Scenario;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void openBrowser() {
        driver = new HtmlUnitDriver(BrowserVersion.FIREFOX, true);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    @After
    public void closeBrowser(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                scenario.attach("Current Page URL is " + driver.getCurrentUrl(), "text", "log");
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "photos", "log");
            } catch (WebDriverException somePlateformsDontSupportScreenshots) {
                System.err.println(somePlateformsDontSupportScreenshots.getMessage());
            }
        }
        driver.quit();
    }
}
