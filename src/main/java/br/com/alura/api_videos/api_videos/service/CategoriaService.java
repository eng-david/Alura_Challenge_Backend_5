package br.com.alura.api_videos.api_videos.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.alura.api_videos.api_videos.dto.CategoriaDto;
import br.com.alura.api_videos.api_videos.dto.CategoriaForm;
import br.com.alura.api_videos.api_videos.dto.CategoriaPutForm;
import br.com.alura.api_videos.api_videos.model.Categoria;

public interface CategoriaService {
    Page<Categoria> findAllCategorias(Pageable pageable);

    Optional<Categoria> findCategoriaById(Long id);

    Categoria saveCategoria(Categoria categoria);

    void deleteCategoriaById(Long id);

    Page<CategoriaDto> toListDto(Page<Categoria> categorias);

    CategoriaDto toDto(Categoria categoria);

    Categoria toCategoria(CategoriaForm form);

    Categoria updateCategoria(Long id, CategoriaPutForm form);
}
