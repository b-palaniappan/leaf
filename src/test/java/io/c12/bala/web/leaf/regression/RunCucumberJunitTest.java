package io.c12.bala.web.leaf.regression;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        plugin = {"pretty", "html:build/cucumber-html-report.html"},
        stepNotifications = true,
        monochrome = true
)
public class RunCucumberJunitTest { }
