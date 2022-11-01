package br.com.alura.api_videos.api_videos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoriaPutForm {
    private String titulo;
    private String cor;
}
