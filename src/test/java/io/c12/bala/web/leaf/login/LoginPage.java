package io.c12.bala.web.leaf.login;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("http://localhost:8880/login")
public class LoginPage extends FluentPage {

    @FindBy(css = "#emailInp")
    private FluentWebElement emailInput;

    @FindBy(css = "#passwordInp")
    private FluentWebElement passwordInput;

    @FindBy(css = "#rememberMeCheckInp")
    private FluentWebElement rememberMeCheckBox;

    @FindBy(css = "#cancelBtn")
    private FluentWebElement cancelButton;

    @FindBy(css = "#loginBtn")
    private FluentWebElement logInButton;

    @FindBy(css = "#forgotPasswordLnk")
    private FluentWebElement forgotPasswordLink;

    @FindBy(css = "#authErrorMsg")
    private FluentWebElement authErrorMessage;

    public LoginPage typeEmailId(String emailId) {
        emailInput.write(emailId);
        return this;
    }

    public LoginPage typePassword(String password) {
        passwordInput.write(password);
        return this;
    }

    public LoginPage checkBoxEnable(boolean enable) {
        if (enable) {
            rememberMeCheckBox.click();
        }
        return this;
    }

    public LoginPage clickLoginButton() {
        logInButton.click();
        return this;
    }

    public String getLoginValidationErrorMessage() {
        return authErrorMessage.text();
    }
}
