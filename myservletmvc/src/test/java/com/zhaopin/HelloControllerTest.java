package com.zhaopin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by guojun.wang on 2017/5/19.
 */
public class HelloControllerTest {
    private MockMvc mockMvc;

    @Test
    public void test1() throws Exception {
        HelloController helloController = new HelloController();
        this.mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();

        this.mockMvc.perform(get("/hello/test")
                .param("name","wgj"))
                .andDo(print())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(status().isOk());
    }
}
