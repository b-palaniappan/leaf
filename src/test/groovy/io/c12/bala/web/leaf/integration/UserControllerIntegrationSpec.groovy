package io.c12.bala.web.leaf.integration

import io.c12.bala.web.leaf.entity.UserEntity
import io.c12.bala.web.leaf.login.LoginPage
import io.c12.bala.web.leaf.login.RegisterPage
import org.fluentlenium.adapter.spock.FluentSpecification
import org.fluentlenium.core.annotation.Page
import org.openqa.selenium.Capabilities
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate

import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserControllerIntegrationSpec extends FluentSpecification {

    @Autowired
    MongoTemplate mongoTemplate

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

    def setup() {
        println "cleanup database tables before test"
        mongoTemplate.dropCollection(UserEntity.class)
    }

    def "call user controller"() {
        when: "call application running in localhost"
        goTo("http://localhost:8880")

        then: "verify the title is index"
        window().title() == "Leaf :: Login"
    }

    // ----------------------------------------------------- Register User -----------------------------------------------------
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
        registerPage.getRegisterMessage() == "User added successfully. Click here to login"
    }

    def "User fill in register user info and click cancel"() {
        given: "goto register page"
        goTo(registerPage)

        when: "enter register page info"
        registerPage.typeFirstName("Jack")
                .typeLastName("Reacher")
                .typeEmail("reacher@c12.io")
                .typePassword("Password@123")
                .typeConfirmPassword("Password@123")
                .checkTermCheckBox(true)

        and: "click cancel button"
        registerPage.clickCancelButton()

        then: "verify if the form is reset"
        registerPage.readFirstName() == ""
        registerPage.readLastName() == ""
    }

    def "Register a user with existing email id"() {
        given: "add user to mongodb"
        mongoTemplate.insert(new UserEntity("asldkfh023-id", "John", "Doe", "john.doe@c12.io", "Password@123", Arrays.asList("ROLE_USER"), true, false, false, LocalDateTime.now(), LocalDateTime.now()))
        goTo(registerPage)

        when: "register with an email id which already exists"
        registerPage.typeFirstName("John")
                .typeLastName("Doe")
                .typeEmail("john.doe@c12.io")
                .typePassword("Password@123")
                .typeConfirmPassword("Password@123")
                .checkTermCheckBox(true)
                .clickRegisterButton()

        then: "verify if registration failed with error message"
        registerPage.getErrorMessage() == "User already exists for email john.doe@c12.io"
    }

    // ----------------------------------------------------- Login User -----------------------------------------------------
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
}
