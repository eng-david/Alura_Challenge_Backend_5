package br.com.alura.api_videos.api_videos.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.alura.api_videos.api_videos.model.Categoria;
import br.com.alura.api_videos.api_videos.model.CategoriaDto;
import br.com.alura.api_videos.api_videos.service.CategoriaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("categorias")
@RequiredArgsConstructor
public class CategoriaRest {

    private final CategoriaService categoriaService;
    
    // read all
    @GetMapping
    public ResponseEntity<List<CategoriaDto>> getAllCategorias(){
        List<Categoria> categorias = categoriaService.findAllCategorias();
        if (categorias.size() > 0) {
            return ResponseEntity.ok(categoriaService.toListDto(categorias));
        }
        return ResponseEntity.noContent().build();
    }

    // read by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoriaById(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaService.findCategoriaById(id);
        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoriaService.toDto(categoria.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
    }
}
