package br.com.alura.api_videos.api_videos.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.alura.api_videos.api_videos.dto.VideoDto;
import br.com.alura.api_videos.api_videos.dto.VideoForm;
import br.com.alura.api_videos.api_videos.dto.VideoPutForm;
import br.com.alura.api_videos.api_videos.model.Video;

public interface VideoService {
    Page<Video> findAllVideos(Pageable pageable);

    Optional<Video> findVideoById(Long id);

    Video saveVideo(Video video);

    void deleteVideoById(Long id);

    Page<VideoDto> toListDto(Page<Video> videos);

    VideoDto toDto(Video video);

    Video toVideo(VideoForm form);

    Video updateVideo(Long id, VideoPutForm form);

    Page<Video> findAllVideosByCategoriaId(Pageable pageable, Long id);

    Page<Video> findByTitulo(Pageable pageable, String search);

}
