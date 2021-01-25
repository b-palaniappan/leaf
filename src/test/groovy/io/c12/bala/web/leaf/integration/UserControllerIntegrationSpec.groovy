package io.c12.bala.web.leaf.integration

import org.fluentlenium.adapter.spock.FluentSpecification
import org.openqa.selenium.Capabilities
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities

class UserControllerIntegrationSpec extends FluentSpecification {

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
        window().title() == "Index"
        and: "check the header value"
        find("h4#header").first().html() == "Welcome Page"
    }
}
