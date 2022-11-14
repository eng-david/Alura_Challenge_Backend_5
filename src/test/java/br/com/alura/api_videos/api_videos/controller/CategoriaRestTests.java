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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.alura.api_videos.api_videos.FetchTokens;
import br.com.alura.api_videos.api_videos.TestTools;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriaRestTests {

    @Autowired
    private MockMvc mockMvc;

    // --- GET, POST, PUT, DELETE No Authenticated ---

    @Test
    @Order(1)
    public void getAllCategoriasWithNoAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/categorias");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(1)
    public void getOneCategoriaWithNoAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/categorias/1");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(1)
    public void postCategoriaWithNoAuthAndReceiveForbidden() throws Exception {
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
    @Order(1)
    public void putCategoriaWithNoAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/categorias/3");

        Map<String, String> categoria = new HashMap<>();
        categoria.put("titulo", "titulo put test");
        String json = TestTools.convertMapToString(categoria);

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(1)
    public void deleteOneCategoriaWithNoAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/categorias/2");
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    // --- PUT, DELETE No Authorized

    @Test
    @Order(2)
    public void putCategoriaWithUserAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/categorias/3");

        Map<String, String> categoria = new HashMap<>();
        categoria.put("titulo", "titulo put test");
        String json = TestTools.convertMapToString(categoria);

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(2)
    public void deleteOneCategoriaWithUserAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/categorias/2");
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    // --- GET, PUT, DELETE Inexistent Item ---

    @Test
    @Order(3)
    public void getNonExistentCategoriaWithUserAuthAndReceiveNotFound() throws Exception {
        URI uri = new URI("/categorias/20");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    }

    @Test
    @Order(3)
    public void putInexistentCategoriaWithAdminAuthAndReceiveNotFound() throws Exception {
        URI uri = new URI("/categorias/20");

        Map<String, String> categoria = new HashMap<>();
        categoria.put("titulo", "titulo put test");
        String json = TestTools.convertMapToString(categoria);

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + FetchTokens.getAdminToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    }

    @Test
    @Order(3)
    public void deleteInexistentCategoriaWithAdminAuthAndReceiveNotFound() throws Exception {
        URI uri = new URI("/categorias/20");
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getAdminToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    }

    // --- GET, POST, PUT, DELETE 200 Success Requisition

    @Test
    @Order(4)
    public void getAllCategoriasWithUserAuthAndReceive200Ok() throws Exception {
        URI uri = new URI("/categorias");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    public void getOneCategoriaWithUserAuthAndReceive200Ok() throws Exception {
        URI uri = new URI("/categorias/1");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    public void postCategoriaWithUserAuthAndReceive201Created() throws Exception {
        URI uri = new URI("/categorias");

        Map<String, String> categoria = new HashMap<>();
        categoria.put("titulo", "categoria test");
        categoria.put("cor", "cor test");
        String json = TestTools.convertMapToString(categoria);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{'titulo':'categoria test'}"))
                .andExpect(MockMvcResultMatchers.content().json("{'cor':'cor test'}"));
    }

    @Test
    @Order(4)
    public void putCategoriaWithAdminAuthAndReceive204() throws Exception {
        URI uri = new URI("/categorias/2");

        Map<String, String> categoria = new HashMap<>();
        categoria.put("titulo", "titulo put test");
        String json = TestTools.convertMapToString(categoria);

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + FetchTokens.getAdminToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(4)
    public void deleteOneCategoriaWithAdminAuthAndReceive204() throws Exception {
        URI uri = new URI("/categorias/3");
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getAdminToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
