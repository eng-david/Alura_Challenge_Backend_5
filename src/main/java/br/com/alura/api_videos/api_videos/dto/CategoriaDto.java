package br.com.alura.api_videos.api_videos.dto;

import br.com.alura.api_videos.api_videos.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoriaDto {

    private Long id;
    private String titulo;
    private String cor;

    public CategoriaDto(Categoria categoria){
        this.id = categoria.getId();
        this.titulo = categoria.getTitulo();
        this.cor = categoria.getCor();
    }

}
