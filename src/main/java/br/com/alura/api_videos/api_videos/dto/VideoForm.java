package br.com.alura.api_videos.api_videos.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Data
public class VideoForm {

    @NotBlank(message = "O campo é obrigatório")
    private String titulo;
    @NotBlank(message = "O campo é obrigatório")
    private String descricao;
    @NotBlank(message = "O campo é obrigatório") @URL
    private String url;
    private Long categoriaId = 1l;


}
