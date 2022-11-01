package br.com.alura.api_videos.api_videos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Categoria> findAllCategorias() {
        log.info("Fetching all categorias");
        return categoriaRepository.findAll();
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
    public List<CategoriaDto> toListDto(List<Categoria> categorias) {
        List<CategoriaDto> categoriasDto = new ArrayList<>();
        categorias.forEach(c -> {
            categoriasDto.add(toDto(c));
        });
        return categoriasDto;
    }

    @Override
    public CategoriaDto toDto(Categoria categoria) {
        log.info("Generating categoria Dto");
        return new CategoriaDto(categoria.getId(), categoria.getTitulo(), categoria.getCor());
    }

    @Override
    public Categoria toCategoria(CategoriaForm form) {
        return new Categoria(null, form.getTitulo(), form.getCor(), null);
    }

    @Override
    public Categoria updateCategoria(Long id, CategoriaPutForm form) {
        Optional<Categoria> categoria = findCategoriaById(id);
        if (categoria.isPresent()) {
            if (form.getTitulo() != null) categoria.get().setTitulo(form.getTitulo());
            if (form.getCor() != null) categoria.get().setCor(form.getCor());
            saveCategoria(categoria.get());
            return categoria.get();
        }
        return null;
    }
    
}