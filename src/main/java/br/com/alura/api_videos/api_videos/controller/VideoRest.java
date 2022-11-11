package br.com.alura.api_videos.api_videos.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.api_videos.api_videos.dto.VideoDto;
import br.com.alura.api_videos.api_videos.dto.VideoForm;
import br.com.alura.api_videos.api_videos.dto.VideoPutForm;
import br.com.alura.api_videos.api_videos.model.Video;
import br.com.alura.api_videos.api_videos.service.VideoServiceImp;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("videos")
@RequiredArgsConstructor
public class VideoRest {

    private final VideoServiceImp videoService;

    // read all videos
    @GetMapping
    private ResponseEntity<Page<VideoDto>> getVideos(@PageableDefault(size = 5) Pageable pageable) {
        Page<Video> videos = videoService.findAllVideos(pageable);
        if (videos.getTotalElements() > 0) {
            return ResponseEntity.ok(videoService.toListDto(videos));
        }
        return ResponseEntity.noContent().build();
    }

    // read video by id
    @GetMapping("/{id}")
    private ResponseEntity<?> getVideoById(@PathVariable Long id) {
        Optional<Video> video = videoService.findVideoById(id);
        if (video.isPresent()) {
            return ResponseEntity.ok(videoService.toDto(video.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
    }

    // search video by name
    @GetMapping("/")
    public ResponseEntity<?> searchVideosByTitulo(@PageableDefault(size = 5) Pageable pageable, String search) {
        Page<Video> videos = videoService.findByTitulo(pageable, search);
        if (videos.getTotalElements() > 0) {
            return ResponseEntity.ok(videoService.toListDto(videos));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
    }

    // read free videos
    @GetMapping("/free")
    private ResponseEntity<?> getFreeVideos() {
        Pageable pageable = PageRequest.of(0, 3);
        Page<Video> freeVideos = videoService.findAllVideos(pageable);
        if (freeVideos.getTotalElements() > 0) {
            return ResponseEntity.ok(videoService.toListDto(freeVideos));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
    }

    // create video
    @PostMapping
    private ResponseEntity<VideoDto> createVideo(@RequestBody @Valid VideoForm form, UriComponentsBuilder uriBuilder) {
        Video video = videoService.toVideo(form);
        videoService.saveVideo(video);
        URI uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(uri).body(videoService.toDto(video));
    }

    // update video by id
    @PutMapping("/{id}")
    private ResponseEntity<?> updateVideoById(@PathVariable Long id, @RequestBody @Valid VideoPutForm form) {
        Video video = videoService.updateVideo(id, form);
        if (video != null) {
            return ResponseEntity.ok(videoService.toDto(video));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");

    }

    // delete video
    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteVideoById(@PathVariable Long id) {
        Optional<Video> video = videoService.findVideoById(id);
        if (video.isPresent()) {
            videoService.deleteVideoById(id);
            return ResponseEntity.ok("Deletado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
    }

}
