package com.example.hello.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ApiController.class)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Valid input: first letter in A-M return 200 and message")
    public void validInputReturns200() throws Exception {
        mockMvc.perform(get("/api/v1/hello-api/hello-world")
                        .param("name", "hari")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Hello Hari"))
                );
    }

    @Test
    @DisplayName("Invalid input: first letter N-Z returns 400 and error")
    void invalidLetterReturns400() throws Exception {
        mockMvc.perform(get("/api/v1/hello-api/hello-world")
                        .param("name", "Roshan")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid Input")));
    }

    @Test
    @DisplayName("Missing or empty name returns 400 and error")
    void missingOrEmptyNameReturns400() throws Exception {
        // Missing Name
        mockMvc.perform(get("/api/v1/hello-api/hello-world")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid Input")));

        // Empty Name
        mockMvc.perform(get("/api/v1/hello-api/hello-world")
                        .param("name", "")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid Input")));

        // Whitespace only
        mockMvc.perform(get("/api/v1/hello-api/hello-world")
                        .param("name", "   ")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid Input")));
    }

    @Test
    @DisplayName("Edge cases: non-letter first char -> invalid")
    void nonLetterFirstCharacterIsInvalid() throws Exception {
        mockMvc.perform(get("/api/v1/hello-api/hello-world")
                        .param("name", "@Hari")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid Input")));

        mockMvc.perform(get("/api/v1/hello-api/hello-world")
                        .param("name", "-Hari")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid Input")));
    }

    @Test
    @DisplayName("Case-insensitivity and capitalization behavior")
    void caseInsensitivityAndCapitalization() throws Exception {
        mockMvc.perform(get("/api/v1/hello-api/hello-world").param("name", "HARI").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Hello Hari")));

        mockMvc.perform(get("/api/v1/hello-api/hello-world").param("name", "hArI").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Hello Hari")));
    }
}
