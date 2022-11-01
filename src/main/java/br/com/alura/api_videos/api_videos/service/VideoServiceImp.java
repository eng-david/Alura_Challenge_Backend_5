package br.com.alura.api_videos.api_videos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.api_videos.api_videos.dto.VideoDto;
import br.com.alura.api_videos.api_videos.dto.VideoForm;
import br.com.alura.api_videos.api_videos.dto.VideoPutForm;
import br.com.alura.api_videos.api_videos.model.Categoria;
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
    private final CategoriaService categoriaService;

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
        log.info("Generating video Dto");
        return new VideoDto(video.getId(), video.getTitulo(), video.getDescricao(), video.getUrl(),
                categoriaService.toDto(video.getCategoria()));
    }

    @Override
    public Video toVideo(VideoForm form) {
        Optional<Categoria> categoria = categoriaService.findCategoriaById(form.getCategoriaId());
        if (categoria.isPresent()) {
            return new Video(null, form.getTitulo(), form.getDescricao(), form.getUrl(), categoria.get());
        }
        return new Video(null, form.getTitulo(), form.getDescricao(), form.getUrl(),
                categoriaService.findCategoriaById(1l).get());
    }

    @Override
    public Video updateVideo(Long id, VideoPutForm form) {
        Optional<Video> video = videoRepository.findById(id);
        if (video.isPresent()) {
            if (form.getTitulo() != null)
                video.get().setTitulo(form.getTitulo());
            if (form.getDescricao() != null)
                video.get().setDescricao(form.getDescricao());
            if (form.getUrl() != null)
                video.get().setUrl(form.getUrl());
            if (form.getCategoriaId() != null) {
                Optional<Categoria> categoria = categoriaService.findCategoriaById(form.getCategoriaId());
                if (categoria.isPresent())
                    video.get().setCategoria(categoria.get());
            }

            saveVideo(video.get());
            return video.get();
        }
        return null;
    }

    @Override
    public List<Video> findAllVideosByCategoriaId(Long id) {
        log.info("Fetching video by categoria id {}", id);
        return videoRepository.findByCategoriaId(id);
    }

    @Override
    public List<Video> findByTitulo(String search) {
        return videoRepository.findByTituloContainingIgnoreCase(search);
    }

}
