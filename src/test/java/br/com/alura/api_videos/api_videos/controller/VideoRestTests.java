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
public class VideoRestTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void getAllVideosWithNoAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/videos");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(2)
    public void getAllVideosWithUserAuthAndReceive200Ok() throws Exception {
        URI uri = new URI("/videos");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(3)
    public void getOneVideoWithNoAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/videos/1");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(4)
    public void getOneVideoWithUserAuthAndReceive200Ok() throws Exception {
        URI uri = new URI("/videos/1");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(5)
    public void getNonExistentVideoWithUserAuthAndReceiveNotFound() throws Exception {
        URI uri = new URI("/videos/6");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    }

    @Test
    @Order(6)
    public void getFreeVideosWithoutAuthAndReceive200Ok() throws Exception {
        URI uri = new URI("/videos/free");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(7)
    public void deleteOneVideoWithUserAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/videos/1");
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(8)
    public void deleteOneVideoWithAdminAuthAndReceive204() throws Exception {
        URI uri = new URI("/videos/1");
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getAdminToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(9)
    public void deleteInexistentVideoWithAdminAuthAndReceiveNotFound() throws Exception {
        URI uri = new URI("/videos/1");
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getAdminToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    }

    @Test
    @Order(10)
    public void putVideoWithUserAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/videos/2");
        Map<String, String> video = new HashMap<>();
        video.put("titulo", "titulo put test");

        String json = TestTools.convertMapToString(video);

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(11)
    public void putVideoWithAdminAuthAndReceive204() throws Exception {
        URI uri = new URI("/videos/2");
        Map<String, String> video = new HashMap<>();
        video.put("titulo", "titulo put test");

        String json = TestTools.convertMapToString(video);

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + FetchTokens.getAdminToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(12)
    public void putInexistentVideoWithAdminAuthAndReceiveNotFound() throws Exception {
        URI uri = new URI("/videos/6");
        Map<String, String> video = new HashMap<>();
        video.put("titulo", "titulo put test");

        String json = TestTools.convertMapToString(video);

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + FetchTokens.getAdminToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    }

    @Test
    @Order(13)
    public void postVideoWithNoAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/videos");
        Map<String, String> video = new HashMap<>();
        video.put("titulo", "titulo test");
        video.put("descricao", "descricao test");
        video.put("url", "http://www.testing.com");

        String json = TestTools.convertMapToString(video);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(14)
    public void postVideoWithUserAuthAndReceive201Created() throws Exception {
        URI uri = new URI("/videos");
        Map<String, String> video = new HashMap<>();
        video.put("titulo", "titulo test");
        video.put("descricao", "descricao test");
        video.put("url", "http://www.testing.com");

        String json = TestTools.convertMapToString(video);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{'titulo':'titulo test'}"))
                .andExpect(MockMvcResultMatchers.content().json("{'descricao':'descricao test'}"))
                .andExpect(MockMvcResultMatchers.content().json("{'url':'http://www.testing.com'}"));

    }

}
