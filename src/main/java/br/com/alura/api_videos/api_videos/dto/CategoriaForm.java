package br.com.alura.api_videos.api_videos.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoriaForm {

    @NotBlank(message = "O campo é obrigatório")
    private String titulo;
    
    @NotBlank(message = "O campo é obrigatório")
    private String cor;
}
