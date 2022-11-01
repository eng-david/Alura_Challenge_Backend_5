package br.com.alura.api_videos.api_videos.config.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
    
    private String field;
    private String message;
}
