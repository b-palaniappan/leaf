package io.c12.bala.web.leaf.integration

import io.c12.bala.web.leaf.login.LoginPage
import io.c12.bala.web.leaf.login.RegisterPage
import org.fluentlenium.adapter.spock.FluentSpecification
import org.fluentlenium.core.annotation.Page
import org.openqa.selenium.Capabilities
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities

class UserControllerIntegrationSpec extends FluentSpecification {

    @Page
    LoginPage loginPage

    @Page
    RegisterPage registerPage

    @Override
    Capabilities getCapabilities() {
        // Configure iBoss proxy
        org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy()
        proxy.setAutodetect(false)
        proxy.setProxyAutoconfigUrl("http://cloud-cluster122197-swg.ibosscloud.com:7080/ibreports/web/pac/proxy.pac")

        // configure htmlunit
        DesiredCapabilities capabilities = new DesiredCapabilities()
        capabilities.setBrowserName("htmlunit")
        capabilities.setVersion("firefox")
        capabilities.setAcceptInsecureCerts(true)
        capabilities.setCapability(CapabilityType.PROXY, proxy)
        return capabilities
    }

    def "call user controller"() {
        when: "call application running in localhost"
        goTo("http://localhost:8080")

        then: "verify the title is index"
        window().title() == "Leaf :: Login"
    }

    def "Login with wrong email and password"() {
        given: "goto login page"
        goTo(loginPage)

        when: "enter email and password"
        loginPage.typeEmailId("dummy@c12.io")
                .typePassword("WrongPassword")
                .checkBoxEnable(true)
                .clickLoginButton()

        then: "verify if authentication error is shown"
        loginPage.getLoginValidationErrorMessage() == "Invalid email address and / or password"
    }

    def "Register a new user"() {
        given: "goto register page"
        goTo(registerPage)

        when: "enter register page info"
        registerPage.typeFirstName("Jack")
                .typeLastName("Reacher")
                .typeEmail("reacher@c12.io")
                .typePassword("Password@123")
                .typeConfirmPassword("Password@123")
                .checkTermCheckBox(true)
                .clickRegisterButton()

        then: "verify if register success message is shown"
        registerPage.getRegisterMessage() == "User added successfully"
    }
}
