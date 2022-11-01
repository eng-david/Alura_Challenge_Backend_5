package br.com.alura.api_videos.api_videos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoDto {

    private Long id;
    private String titulo;
    private String descricao;
    private String url;
    private CategoriaDto categoriaDto;

}
