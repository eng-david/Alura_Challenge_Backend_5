package br.com.alura.api_videos.api_videos;

import java.util.Map;
import java.util.stream.Collectors;

public class TestTools {

    public static String convertMapToString(Map<String, String> map) {
        return map.keySet().stream()
                .map(key -> "\"" + key + "\":\"" + map.get(key) + "\"")
                .collect(Collectors.joining(",", "{", "}"));
    }
    
}
