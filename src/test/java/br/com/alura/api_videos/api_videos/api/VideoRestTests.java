package br.com.alura.api_videos.api_videos.api;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VideoRestTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void getAllVideosAndReceive200Ok() throws Exception {
        URI uri = new URI("/videos");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    public void getOneVideoAndReceive200Ok() throws Exception {
        URI uri = new URI("/videos/1");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(3)
    public void getNonExistentVideoAndReceiveNotFound() throws Exception {
        URI uri = new URI("/videos/6");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    }

    @Test
    @Order(4)
    public void deleteOneVideoAndReceive200Ok() throws Exception {
        URI uri = new URI("/videos/1");
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Deletado"));
    }

    @Test
    @Order(5)
    public void deleteInexistentVideoAndReceiveNotFound() throws Exception {
        URI uri = new URI("/videos/1");
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    }

    @Test
    @Order(6)
    public void putVideoAndReceive200Ok() throws Exception {
        URI uri = new URI("/videos/2");
        Map<String, String> video = new HashMap<>();
        video.put("titulo", "titulo put test");

        String json = convertMapToString(video);

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'titulo':'titulo put test'}"));
    }

    @Test
    @Order(7)
    public void putVideoAndReceiveNotFound() throws Exception {
        URI uri = new URI("/videos/6");
        Map<String, String> video = new HashMap<>();
        video.put("titulo", "titulo put test");

        String json = convertMapToString(video);

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    }

    @Test
    @Order(8)
    public void postVideoAndReceive201Created() throws Exception {
        URI uri = new URI("/videos");
        Map<String, String> video = new HashMap<>();
        video.put("titulo", "titulo test");
        video.put("descricao", "descricao test");
        video.put("url", "http://www.testing.com");

        String json = convertMapToString(video);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{'titulo':'titulo test'}"));
    }

    private String convertMapToString(Map<String, String> map) {
        return map.keySet().stream()
                .map(key -> "\"" + key + "\":\"" + map.get(key) + "\"")
                .collect(Collectors.joining(",", "{", "}"));
    }

}
