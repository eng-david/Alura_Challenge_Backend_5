package br.com.alura.api_videos.api_videos.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.api_videos.api_videos.dto.CategoriaDto;
import br.com.alura.api_videos.api_videos.dto.CategoriaForm;
import br.com.alura.api_videos.api_videos.dto.CategoriaPutForm;
import br.com.alura.api_videos.api_videos.model.Categoria;
import br.com.alura.api_videos.api_videos.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoriaServiceImp implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public Optional<Categoria> findCategoriaById(Long id) {
        log.info("Fetching categoria id {}", id);
        return categoriaRepository.findById(id);
    }

    @Override
    public Page<Categoria> findAllCategorias(Pageable pageable) {
        log.info("Fetching all categorias");
        return categoriaRepository.findAll(pageable);
    }

    @Override
    public Categoria saveCategoria(Categoria categoria) {
        log.info("Saving categoria");
        categoriaRepository.save(categoria);
        return categoria;
    }

    @Override
    public void deleteCategoriaById(Long id) {
        log.info("Deleting categoria");
        categoriaRepository.deleteById(id);
    }

    @Override
    public Page<CategoriaDto> toListDto(Page<Categoria> categorias) {
        return categorias.map(CategoriaDto::new);
    }

    @Override
    public CategoriaDto toDto(Categoria categoria) {
        log.info("Generating categoria Dto");
        return new CategoriaDto(categoria);
    }

    @Override
    public Categoria toCategoria(CategoriaForm form) {
        return new Categoria(null, form.getTitulo(), form.getCor(), null);
    }

    @Override
    public Categoria updateCategoria(Long id, CategoriaPutForm form) {
        Optional<Categoria> categoria = findCategoriaById(id);
        if (categoria.isPresent()) {
            if (form.getTitulo() != null)
                categoria.get().setTitulo(form.getTitulo());
            if (form.getCor() != null)
                categoria.get().setCor(form.getCor());
            saveCategoria(categoria.get());
            return categoria.get();
        }
        return null;
    }

}
