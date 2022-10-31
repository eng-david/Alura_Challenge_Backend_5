package br.com.alura.api_videos.api_videos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.api_videos.api_videos.model.Video;
import br.com.alura.api_videos.api_videos.model.VideoDto;
import br.com.alura.api_videos.api_videos.model.VideoForm;
import br.com.alura.api_videos.api_videos.model.VideoPutForm;
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
    public List<Video> findAllVideos() {
        log.info("Fetching all videos");
        return videoRepository.findAll();
    }

    @Override
    public Video saveVideo(Video video) {
        log.info("Saving video");
        videoRepository.save(video);
        return video;
    }

    @Override
    public Optional<Video> findVideoById(Long id) {
        log.info("Fetching video id {}", id);
        return videoRepository.findById(id);
    }

    @Override
    public void deleteVideoById(Long id) {
        log.info("Deleting video id {}", id);
        videoRepository.deleteById(id);
    }

    @Override
    public List<VideoDto> toListDto(List<Video> videos) {

        List<VideoDto> videosDto = new ArrayList<>();
        videos.forEach(v -> {
            videosDto.add(toDto(v));
        });
        return videosDto;
        // return videos.stream().map(VideoDto::new).collect(Collectors.toList());
    }

    @Override
    public VideoDto toDto(Video video) {
        return new VideoDto(video.getId(), video.getTitulo(), video.getDescricao(), video.getUrl());
    }

    @Override
    public Video toVideo(VideoForm form) {
        Video video = new Video();
        video.setTitulo(form.getTitulo());
        video.setDescricao(form.getDescricao());
        video.setUrl(form.getUrl());
        return video;
    }

    @Override
    public Video updateVideo(Long id, VideoPutForm form) {
        Optional<Video> video = videoRepository.findById(id);
        if (video.isPresent()) {
            if (form.getTitulo() != null) video.get().setTitulo(form.getTitulo());
            if (form.getDescricao() != null) video.get().setDescricao(form.getDescricao());
            if (form.getUrl() != null) video.get().setUrl(form.getUrl());
            saveVideo(video.get());
            return video.get();
        }
        return null;
    }


}
