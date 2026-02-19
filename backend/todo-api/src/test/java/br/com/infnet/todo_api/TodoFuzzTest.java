package br.com.infnet.todo_api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class TodoFuzzTest {

    @Autowired
    private MockMvc mockMvc;

    private static final List<String> entradasInvalidas = Arrays.asList("", "   ", "A".repeat(201));

    private static final List<String> entradasExtremasValidas = Arrays.asList("A".repeat(198), ";':,.<>/?!@#$%^&*()_+-=[]{}|",  "🚢🎄❌❗🎋🩰", "A               ");

    @Test
    @DisplayName("Fuzz test: entradas inválidas")
    public void fuzzEntradasInvalidas() throws Exception {
        for (String entrada : entradasInvalidas) {
            String payload = String.format("{\"title\":\"%s\"}", entrada);

            mockMvc.perform(post("/api/todos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    @DisplayName("Fuzz test: título null")
    public void fuzzEntradaNull() throws Exception {
        String payload = "{\"title\":null}";

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Fuzz test: entradas extremas válidas")
    public void fuzzEntradasExtremasValidas() throws Exception {
        for (String entrada : entradasExtremasValidas) {
            String payload = String.format("{\"title\":\"%s\"}", entrada);

            mockMvc.perform(post("/api/todos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isCreated());
        }
    }
}
