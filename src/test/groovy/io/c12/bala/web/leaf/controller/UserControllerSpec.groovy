package io.c12.bala.web.leaf.controller

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

class UserControllerSpec extends Specification {

    UserController userController = new UserController()
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build()

    def "user controller unit test"() {
        when: "controller is called"
        def result = mockMvc.perform(get("/"))

        then: "verify the response"
        result.andDo(print()).andReturn()
        result.andExpect(status().isOk())
    }
}
