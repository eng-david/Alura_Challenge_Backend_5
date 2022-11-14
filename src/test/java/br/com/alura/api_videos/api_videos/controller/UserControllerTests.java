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
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    // --- GET, POST No Authenticated ---

    @Test
    @Order(1)
    public void getAllUsersWithNoAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/users");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(1)
    public void getOneUserWithNoAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/users/?username=admin");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(1)
    public void postUserWithNoAuthAndReceiveForbidden() throws Exception {
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
    @Order(1)
    public void postAuthorityToUserWithNoAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/users/authoritytouser");

        Map<String, String> user = new HashMap<>();
        user.put("username", "adminTest");
        user.put("authority", "ROLE_ADMIN");
        String json = TestTools.convertMapToString(user);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    
    // --- GET, POST No Authorized

    @Test
    @Order(2)
    public void getAllUsersWithUserAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/users");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(4)
    public void getOneUserWithUserAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/users/?username=admin");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(6)
    public void postUserWithUserAuthAndReceiveForbidden() throws Exception {
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
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(8)
    public void postAuthorityToUserWithUserAuthAndReceiveForbidden() throws Exception {
        URI uri = new URI("/users/authoritytouser");

        Map<String, String> user = new HashMap<>();
        user.put("username", "adminTest");
        user.put("authority", "ROLE_ADMIN");
        String json = TestTools.convertMapToString(user);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + FetchTokens.getUserToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    
    // --- GET Inexistent Item ---

    @Test
    @Order(5)
    public void getNonExistentUserWithAdminAuthAndReceiveNotFound() throws Exception {
        URI uri = new URI("/users/?username=notExists");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getAdminToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não encontrado"));
    }

    // --- GET, POST 200 Success Requisition

    @Test
    @Order(3)
    public void getAllUsersWithAdminAuthAndReceive200Ok() throws Exception {
        URI uri = new URI("/users");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getAdminToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    public void getOneUserWithAdminAuthAndReceive200Ok() throws Exception {
        URI uri = new URI("/users/?username=user");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", ("Bearer " + FetchTokens.getAdminToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(7)
    public void postUserWithAdminAuthAndReceive201Created() throws Exception {
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
                .header("Authorization", ("Bearer " + FetchTokens.getAdminToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{'name':'nameTest'}"))
                .andExpect(MockMvcResultMatchers.content().json("{'username':'adminTest'}"));
    }

    @Test
    @Order(8)
    public void postAuthorityToUserWithAdminAuthAndReceive200Ok() throws Exception {
        URI uri = new URI("/users/authoritytouser");

        Map<String, String> user = new HashMap<>();
        user.put("username", "adminTest");
        user.put("authority", "ROLE_ADMIN");
        String json = TestTools.convertMapToString(user);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ("Bearer " + FetchTokens.getAdminToken(mockMvc))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'name':'nameTest'}"))
                .andExpect(MockMvcResultMatchers.content().json("{'username':'adminTest'}"));
    }

}
