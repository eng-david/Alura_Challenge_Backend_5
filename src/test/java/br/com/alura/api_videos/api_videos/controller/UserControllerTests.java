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
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static String adminToken;

    @Test
    @Order(1)
    public void getAdminToken() throws Exception {
        
        URI loginUri = new URI("/login");
        
        Map<String, String> auth = new HashMap<>();
        auth.put("username", "admin");
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
                adminToken = tokenNode.textValue();
    }

    @Test
    @Order(2)
    public void getAllUsersAndReceiveForbidden() throws Exception {
        URI uri = new URI("/users");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    
    @Test
    @Order(3)
    public void getAllUsersAndReceive200Ok() throws Exception {
        URI uri = new URI("/users");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + adminToken)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    public void getOneUserAndReceive200Ok() throws Exception {
        URI uri = new URI("/users/?username=admin");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + adminToken)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(5)
    public void getNonExistentUserAndReceiveNotFound() throws Exception {
        URI uri = new URI("/users/?username=notExists");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + adminToken)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    }

    @Test
    @Order(6)
    public void postUserAndReceiveForbidden() throws Exception {
        URI uri = new URI("/users");

        Map<String, String> user = new HashMap<>();
        user.put("name", "nameTest");
        user.put("username", "adminTest");
        user.put("password", "1234");
        String json = TestTools.convertMapToString(user);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(7)
    public void postUserAndReceive201Created() throws Exception {
        URI uri = new URI("/users");

        Map<String, String> user = new HashMap<>();
        user.put("name", "nameTest");
        user.put("username", "adminTest");
        user.put("password", "1234");
        String json = TestTools.convertMapToString(user);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + adminToken)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{'name':'nameTest'}"))
                .andExpect(MockMvcResultMatchers.content().json("{'username':'adminTest'}"));
    }

    @Test
    @Order(8)
    public void postAuthorityToUserAndReceive200Ok() throws Exception {
        URI uri = new URI("/users/authoritytouser");

        Map<String, String> user = new HashMap<>();
        user.put("username", "adminTest");
        user.put("authority", "ROLE_ADMIN");
        String json = TestTools.convertMapToString(user);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + adminToken)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'name':'nameTest'}"))
                .andExpect(MockMvcResultMatchers.content().json("{'username':'adminTest'}"));
    }

    @Test
    @Order(9)
    public void getNewAdminToken() throws Exception {
        
        URI loginUri = new URI("/login");
        
        Map<String, String> auth = new HashMap<>();
        auth.put("username", "adminTest");
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
                adminToken = tokenNode.textValue();
    }

    @Test
    @Order(10)
    public void getAllUsersWithNewTokenAndReceive200Ok() throws Exception {
        URI uri = new URI("/users");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + adminToken)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



}
