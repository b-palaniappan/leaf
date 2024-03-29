package io.c12.bala.web.leaf.integration

import io.c12.bala.web.leaf.entity.UserEntity
import io.c12.bala.web.leaf.login.HomePage
import io.c12.bala.web.leaf.login.LoginPage
import io.c12.bala.web.leaf.login.RegisterPage
import org.fluentlenium.adapter.spock.FluentSpecification
import org.fluentlenium.core.annotation.Page
import org.openqa.selenium.Capabilities
import org.openqa.selenium.remote.DesiredCapabilities
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.data.mongodb.core.MongoTemplate

import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationSpec extends FluentSpecification {

    @LocalServerPort
    private Integer port;

    @Autowired
    MongoTemplate mongoTemplate

    @Page
    LoginPage loginPage

    @Page
    RegisterPage registerPage

    @Page
    HomePage homePage

    @Override
    Capabilities getCapabilities() {

        // configure htmlunit
        DesiredCapabilities capabilities = new DesiredCapabilities()
        capabilities.setBrowserName("htmlunit")
        capabilities.setVersion("firefox")
        capabilities.setAcceptInsecureCerts(true)
        return capabilities
    }

    def setup() {
        println "cleanup database tables before test"
        mongoTemplate.dropCollection(UserEntity.class)
        setBaseUrl("http://localhost:" + port)
    }

    def "call user controller"() {
        when: "call application running in localhost"
        goTo("http://localhost:" + port)

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

    def "Login with valid email and password"() {
        given: "register a user"
        goTo(registerPage)
        registerPage.typeFirstName("John")
                .typeLastName("Doe")
                .typeEmail("john.doe@c12.io")
                .typePassword("Password@123")
                .typeConfirmPassword("Password@123")
                .checkTermCheckBox(true)
                .clickRegisterButton()

        and: "goto login page"
        goTo(loginPage)

        when: "enter email and password"
        loginPage.typeEmailId("john.doe@c12.io")
                .typePassword("Password@123")
                .checkBoxEnable(true)
                .clickLoginButton()

        then: "verify if authentication error is shown"
        homePage.getLogoutButtonText() == "Log out"
    }
}
