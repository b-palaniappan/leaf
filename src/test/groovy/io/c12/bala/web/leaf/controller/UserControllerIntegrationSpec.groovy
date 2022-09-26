package io.c12.bala.web.leaf.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    def "user controller integration test for home page"() {
        when: "controller is called"
        def result = mockMvc.perform(get("/home"))

        then: "verify the response"
        result.andDo(print()).andReturn()
        result.andExpect(status().isOk())
        result.andExpect(view().name("index"))
    }

    def "user controller integration test for registration page"() {
        when: "controller is called"
        def result = mockMvc.perform(get("/register"))

        then: "verify the response"
        result.andDo(print()).andReturn()
        result.andExpect(status().isOk())
        result.andExpect(view().name("register"))
    }
}
