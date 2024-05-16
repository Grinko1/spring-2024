package org.spring.mvc.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductsControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getNewProductPage_ReturnsProductPage()throws Exception{
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/catalog/products/create")
                .with(user("test2@mail.com").roles("MANAGER") );
        //when
        this.mockMvc.perform(requestBuilder)
        //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name("catalog/products/new_product")
                );
    }
}
