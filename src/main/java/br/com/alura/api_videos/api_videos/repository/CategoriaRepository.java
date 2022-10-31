package br.com.alura.api_videos.api_videos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.api_videos.api_videos.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
