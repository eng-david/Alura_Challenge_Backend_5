package br.com.alura.api_videos.api_videos.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Video> findAllVideos(Pageable pageable) {
        log.info("Fetching all videos");
        return videoRepository.findAll(pageable);
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
    public Page<VideoDto> toListDto(Page<Video> videos) {
        return videos.map(VideoDto::new);
    }

    @Override
    public VideoDto toDto(Video video) {
        log.info("Generating video Dto");
        return new VideoDto(video.getId(), video.getTitulo(), video.getDescricao(), video.getUrl(),
                video.getCategoria().getId());
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
    public Page<Video> findAllVideosByCategoriaId(Pageable pageable, Long id) {
        log.info("Fetching video by categoria id {}", id);
        return videoRepository.findByCategoriaId(pageable, id);
    }

    @Override
    public Page<Video> findByTitulo(Pageable pageable, String search) {
        return videoRepository.findByTituloContainingIgnoreCase(pageable, search);
    }

}
