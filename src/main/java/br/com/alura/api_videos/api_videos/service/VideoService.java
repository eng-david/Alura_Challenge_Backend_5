package br.com.alura.api_videos.api_videos.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import br.com.alura.api_videos.api_videos.model.Video;
import br.com.alura.api_videos.api_videos.model.VideoDto;
import br.com.alura.api_videos.api_videos.model.VideoForm;
import br.com.alura.api_videos.api_videos.model.VideoPutForm;

public interface VideoService {
    List<Video> findAllVideos();

    Optional<Video> findVideoById(Long id);

    Video saveVideo(Video video);

    void deleteVideoById(Long id);

    List<VideoDto> toListDto(List<Video> videos);

    VideoDto toDto(Video video);

    Video toVideo(@Valid VideoForm form);

    Video updateVideo(Long id, VideoPutForm form);

}
