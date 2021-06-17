package io.c12.bala.web.leaf.login;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("http://localhost:8880/register")
public class RegisterPage extends FluentPage {

    @FindBy(css = "#firstNameInp")
    private FluentWebElement firstNameInput;

    @FindBy(css = "#lastNameInp")
    private FluentWebElement lastNameInput;

    @FindBy(css = "#emailInp")
    private FluentWebElement emailInput;

    @FindBy(css = "#passwordInp")
    private FluentWebElement passwordInput;

    @FindBy(css = "#confirmPasswordInp")
    private FluentWebElement confirmPasswordInput;

    @FindBy(css = "#termsCheckInp")
    private FluentWebElement termCheckbox;

    @FindBy(css = "#cancelBtn")
    private FluentWebElement cancelButton;

    @FindBy(css = "#registerBtn")
    private FluentWebElement registerButton;

    @FindBy(css = "#modelOkayBtn")
    private FluentWebElement modelOkayButton;

    @FindBy(css = "#registerMessage")
    private FluentWebElement registerMessage;

    @FindBy(css = "#errorMessage")
    private FluentWebElement errorMessage;

    public RegisterPage typeFirstName(String firstName) {
        firstNameInput.write(firstName);
        return this;
    }

    public RegisterPage typeLastName(String lastName) {
        lastNameInput.write(lastName);
        return this;
    }

    public RegisterPage typeEmail(String email) {
        emailInput.write(email);
        return this;
    }

    public RegisterPage typePassword(String password) {
        passwordInput.write(password);
        return this;
    }

    public RegisterPage typeConfirmPassword(String confirmPassword) {
        confirmPasswordInput.write(confirmPassword);
        return this;
    }

    public RegisterPage checkTermCheckBox(boolean enable) {
        if (enable) {
            termCheckbox.click();
        }
        return this;
    }

    public RegisterPage clickCancelButton() {
        cancelButton.click();
        return this;
    }

    public RegisterPage clickRegisterButton() {
        registerButton.click();
        return this;
    }

    public RegisterPage clickModelOkayButton() {
        modelOkayButton.click();
        return this;
    }

    public String getRegisterMessage() {
        return registerMessage.text();
    }

    public String getErrorMessage() {
        return errorMessage.text();
    }

    public String readFirstName() {
        return firstNameInput.value();
    }

    public String readLastName() {
        return lastNameInput.text();
    }
}
