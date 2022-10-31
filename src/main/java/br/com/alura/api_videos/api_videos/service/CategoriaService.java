package br.com.alura.api_videos.api_videos.service;

import java.util.List;
import java.util.Optional;

import br.com.alura.api_videos.api_videos.model.Categoria;
import br.com.alura.api_videos.api_videos.model.CategoriaDto;

public interface CategoriaService {
    List<Categoria> findAllCategorias();

    Optional<Categoria> findCategoriaById(Long id);

    Categoria saveCategoria(Categoria categoria);

    void deleteCategoriaById(Long id);

    List<CategoriaDto> toListDto(List<Categoria> categorias);

    CategoriaDto toDto(Categoria categoria);
}
