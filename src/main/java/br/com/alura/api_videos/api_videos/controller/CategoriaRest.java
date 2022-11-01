package br.com.alura.api_videos.api_videos.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.api_videos.api_videos.dto.CategoriaDto;
import br.com.alura.api_videos.api_videos.dto.CategoriaForm;
import br.com.alura.api_videos.api_videos.dto.CategoriaPutForm;
import br.com.alura.api_videos.api_videos.model.Categoria;
import br.com.alura.api_videos.api_videos.model.Video;
import br.com.alura.api_videos.api_videos.service.CategoriaService;
import br.com.alura.api_videos.api_videos.service.VideoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("categorias")
@RequiredArgsConstructor
public class CategoriaRest {

    private final CategoriaService categoriaService;
    private final VideoService videoService;
    
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

    // create
    @PostMapping
    public ResponseEntity<CategoriaDto> createCategoria(@RequestBody @Valid CategoriaForm form, UriComponentsBuilder uriBuilder) {
        Categoria categoria = categoriaService.toCategoria(form);
        categoriaService.saveCategoria(categoria);
        URI uri = uriBuilder.path("categorias/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).body(categoriaService.toDto(categoria));
    }

    // update by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoria(@PathVariable Long id, @RequestBody @Valid CategoriaPutForm form) {
        if (id == 1) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não permitido");
        Categoria categoria = categoriaService.updateCategoria(id, form);
        if (categoria != null){
            return ResponseEntity.ok(categoriaService.toDto(categoria));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
    }

    // delete by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoria(@PathVariable Long id) {
        if (id == 1) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não permitido");
        Optional<Categoria> categoria = categoriaService.findCategoriaById(id);
        if (categoria.isPresent()) {
            categoriaService.deleteCategoriaById(id);
            return ResponseEntity.ok("Deletado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
    }

    // read all videos by categoria
    @GetMapping("/{id}/videos")
    public ResponseEntity<?> getVideosByCategoria(@PathVariable Long id) {
        List<Video> videos = videoService.findAllVideosByCategoriaId(id);
        if (videos.size() > 0){
            return ResponseEntity.ok(videoService.toListDto(videos));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
    }
}
