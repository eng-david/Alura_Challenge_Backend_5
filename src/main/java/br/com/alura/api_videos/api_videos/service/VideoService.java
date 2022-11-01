package br.com.alura.api_videos.api_videos.service;

import java.util.List;
import java.util.Optional;

import br.com.alura.api_videos.api_videos.dto.VideoDto;
import br.com.alura.api_videos.api_videos.dto.VideoForm;
import br.com.alura.api_videos.api_videos.dto.VideoPutForm;
import br.com.alura.api_videos.api_videos.model.Video;

public interface VideoService {
    List<Video> findAllVideos();

    Optional<Video> findVideoById(Long id);

    Video saveVideo(Video video);

    void deleteVideoById(Long id);

    List<VideoDto> toListDto(List<Video> videos);

    VideoDto toDto(Video video);

    Video toVideo(VideoForm form);

    Video updateVideo(Long id, VideoPutForm form);

    List<Video> findAllVideosByCategoriaId(Long id);

    List<Video> findByTitulo(String search);

}
