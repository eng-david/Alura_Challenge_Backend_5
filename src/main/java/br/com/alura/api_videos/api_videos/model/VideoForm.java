package br.com.alura.api_videos.api_videos.model;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Data
public class VideoForm {

    @NotBlank
    private String titulo;
    @NotBlank
    private String descricao;
    @NotBlank @URL
    private String url;

}
