package br.com.alura.api_videos.api_videos.model;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Data
public class VideoPutForm {

    private String titulo;
    private String descricao;
    @URL
    private String url;

}
