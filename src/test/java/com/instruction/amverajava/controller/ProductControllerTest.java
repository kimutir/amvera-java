package com.instruction.amverajava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instruction.amverajava.models.Product;
import com.instruction.amverajava.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void set() {
        productRepository.deleteAll();
    }

    @Test
    void initiallyShouldReturnEmptyProductList() throws Exception {
        mockMvc.perform(get("/product"))
                .andExpect(status().isOk());
        assertThat(productRepository.findAll()).hasSize(0);
    }

    @Test
    void shouldSaveProduct() throws Exception {
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createProduct())))
                .andExpect(status().isOk());

        assertThat(productRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldFindProductByName() throws Exception {
        productRepository.save(createProduct());

        mockMvc.perform(get("/product/find?name={name}", "Kiwi"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Kiwi"))
                .andExpect(jsonPath("$.amount").value(3));
    }

    private Product createProduct() {
        return new Product("Kiwi", 3, 300.0);
    }


}