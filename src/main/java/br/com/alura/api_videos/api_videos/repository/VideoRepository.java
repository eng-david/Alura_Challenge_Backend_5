package br.com.alura.api_videos.api_videos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.api_videos.api_videos.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByCategoriaId(Long id);

    List<Video> findByTituloContainingIgnoreCase(String search);
        
}
