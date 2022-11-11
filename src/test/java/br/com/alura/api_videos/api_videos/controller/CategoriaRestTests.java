package br.com.alura.api_videos.api_videos.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.api_videos.api_videos.TestTools;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriaRestTests {

    @Autowired
    private MockMvc mockMvc;

    private static String userToken;

    @Test
    @Order(1)
    public void getUserToken() throws Exception {
        
        URI loginUri = new URI("/login");
        
        Map<String, String> auth = new HashMap<>();
        auth.put("username", "user");
        auth.put("password", "1234");
        String json = TestTools.convertMapToString(auth);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(loginUri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'type':'Bearer'}"))
                .andReturn();
        
                String responseString = result.getResponse().getContentAsString();
                JsonNode tokenNode = new ObjectMapper().readTree(responseString).path("token");
                userToken = tokenNode.textValue();
    }

    @Test
    @Order(2)
    public void getAllCategoriasAndReceiveForbidden() throws Exception {
        URI uri = new URI("/categorias");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    
    @Test
    @Order(3)
    public void getAllCategoriasAndReceive200Ok() throws Exception {
        URI uri = new URI("/categorias");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + userToken)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    public void getOneCategoriaAndReceive200Ok() throws Exception {
        URI uri = new URI("/categorias/1");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + userToken)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(5)
    public void getNonExistentCategoriaAndReceiveNotFound() throws Exception {
        URI uri = new URI("/categorias/9");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + userToken)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    }

    @Test
    @Order(6)
    public void deleteOneCategoriaAndReceiveForbidden() throws Exception {
        URI uri = new URI("/categorias/2");
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .header("Authorization", ("Bearer " + userToken)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    // @Test
    // @Order(5)
    // public void deleteInexistentCategoriaAndReceiveNotFound() throws Exception {
    //     URI uri = new URI("/categorias/2");
    //     mockMvc.perform(MockMvcRequestBuilders
    //             .delete(uri))
    //             .andExpect(MockMvcResultMatchers.status().isNotFound())
    //             .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    // }

    @Test
    @Order(7)
    public void putCategoriaAndReceiveForbidden() throws Exception {
        URI uri = new URI("/categorias/3");

        Map<String, String> categoria = new HashMap<>();
        categoria.put("titulo", "titulo put test");
        String json = TestTools.convertMapToString(categoria);

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + userToken)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
                }

    // @Test
    // @Order(7)
    // public void putInexistentCategoriaAndReceiveNotFound() throws Exception {
    //     URI uri = new URI("/categorias/9");

    //     Map<String, String> categoria = new HashMap<>();
    //     categoria.put("titulo", "titulo put test");
    //     String json = TestTools.convertMapToString(categoria);

    //     mockMvc.perform(MockMvcRequestBuilders
    //             .put(uri)
    //             .content(json)
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(MockMvcResultMatchers.status().isNotFound())
    //             .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    // }

    @Test
    @Order(8)
    public void postCategoriaAndReceiveForbidden() throws Exception {
        URI uri = new URI("/categorias");

        Map<String, String> categoria = new HashMap<>();
        categoria.put("titulo", "categoria test");
        categoria.put("cor", "cor test");
        String json = TestTools.convertMapToString(categoria);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(9)
    public void postCategoriaAndReceive201Created() throws Exception {
        URI uri = new URI("/categorias");

        Map<String, String> categoria = new HashMap<>();
        categoria.put("titulo", "categoria test");
        categoria.put("cor", "cor test");
        String json = TestTools.convertMapToString(categoria);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + userToken)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{'titulo':'categoria test'}"))
                .andExpect(MockMvcResultMatchers.content().json("{'cor':'cor test'}"));
    }

}
