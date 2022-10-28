package br.com.alura.api_videos.api_videos.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.api_videos.api_videos.model.Video;
import br.com.alura.api_videos.api_videos.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VideoServiceImp implements VideoService {

    private final VideoRepository videoRepository;

    @Override
    public Video getVideo(Long id) {
        log.info("Fetching video id {}", id);
        Optional<Video> video = videoRepository.findById(id);
        if (video.isPresent()) {
            return video.get();
        }
        return null;
    }

    @Override
    public List<Video> getAllVideos() {
        log.info("Fetching all videos");
        return videoRepository.findAll();
    }

    @Override
    public Video saveVideo(Video video) {
        videoRepository.save(video);
        return video;
    }

    @Override
    public Optional<Video> findById(Long id) {
        return videoRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        videoRepository.deleteById(id);
    }
    
}
