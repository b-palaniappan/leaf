package io.c12.bala.web.leaf.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    def "user controller integration test"() {
        when: "controller is called"
        def result = mockMvc.perform(get("/"))

        then: "verify the response"
        result.andDo(print()).andReturn()
        result.andExpect(status().isOk())
    }
}
