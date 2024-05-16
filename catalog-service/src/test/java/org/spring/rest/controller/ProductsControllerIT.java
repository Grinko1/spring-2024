package org.spring.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ProductsControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/sql/products.sql")
    void getAll_ReturnsProductsList() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/api/products")
                .param("filter", "first")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalog")));

        //when
        this.mockMvc.perform(requestBuilder)

                //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                {"id":1, "title" :"first", "details":"first details"},
                                {"id":3, "title" :"third", "details":"third details"}
                                ]
                                """)
                );
    }
}