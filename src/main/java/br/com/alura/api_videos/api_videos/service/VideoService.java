package br.com.alura.api_videos.api_videos.service;

import java.util.List;
import java.util.Optional;

import br.com.alura.api_videos.api_videos.model.Video;

public interface VideoService {
    Video getVideo(Long id);
    List<Video> getAllVideos();
    Video saveVideo(Video video);
    Optional<Video> findById(Long id);
    void deleteById(Long id);
}
