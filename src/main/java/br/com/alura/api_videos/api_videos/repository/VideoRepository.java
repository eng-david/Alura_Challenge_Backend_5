package br.com.alura.api_videos.api_videos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.api_videos.api_videos.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Page<Video> findByCategoriaId(Pageable pageable, Long id);

    Page<Video> findByTituloContainingIgnoreCase(Pageable pageable, String search);
        
}
