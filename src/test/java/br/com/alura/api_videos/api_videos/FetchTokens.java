package br.com.alura.api_videos.api_videos;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FetchTokens {

    private static String userToken;
    private static String adminToken;

    public static String getUserToken(MockMvc mockMvc) {

        if (userToken != null)
            return userToken;

        Map<String, String> auth = new HashMap<>();
        auth.put("username", "user");
        auth.put("password", "1234");
        String json = TestTools.convertMapToString(auth);

        userToken = getToken(mockMvc, json);
        return userToken;

    }

    public static String getAdminToken(MockMvc mockMvc) {

        if (adminToken != null)
            return adminToken;

        Map<String, String> auth = new HashMap<>();
        auth.put("username", "admin");
        auth.put("password", "1234");
        String json = TestTools.convertMapToString(auth);

        adminToken = getToken(mockMvc, json);
        return adminToken;

    }

    private static String getToken(MockMvc mockMvc, String json) {
        try {
            URI loginUri = new URI("/login");
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                    .post(loginUri)
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            String responseString = result.getResponse().getContentAsString();
            JsonNode tokenNode = new ObjectMapper().readTree(responseString).path("token");
            return tokenNode.textValue();
        } catch (Exception e) {
            throw new BadCredentialsException("login error");
        }
    }

}
