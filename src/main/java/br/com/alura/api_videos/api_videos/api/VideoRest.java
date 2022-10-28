package br.com.alura.api_videos.api_videos.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import br.com.alura.api_videos.api_videos.model.Video;
import br.com.alura.api_videos.api_videos.model.VideoDto;
import br.com.alura.api_videos.api_videos.model.VideoForm;
import br.com.alura.api_videos.api_videos.model.VideoPutForm;
import br.com.alura.api_videos.api_videos.service.VideoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("videos")
@RequiredArgsConstructor
public class VideoRest {

    private final VideoService videoService;

    // read all videos
    @GetMapping
    private ResponseEntity<List<VideoDto>> getVideos(){
        List<Video> videos = videoService.getAllVideos();
    
        if (videos.size() > 0){
            List<VideoDto> videosDto = VideoDto.toDto(videos);
            return ResponseEntity.ok(videosDto);
        }
        return ResponseEntity.noContent().build();
    }    

    // read video by id
    @GetMapping("/{id}")
    private ResponseEntity<VideoDto> getVideoById(@PathVariable Long id){
        Optional<Video> video = videoService.findById(id);
        if (video.isPresent()) {
            return ResponseEntity.ok(new VideoDto(video.get()) );
        }

        return ResponseEntity.notFound().build();
    }

    // delete video
    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteVideoById(@PathVariable Long id) {
        Optional<Video> video = videoService.findById(id);
        if (video.isPresent()) {
            videoService.deleteById(id);
            return ResponseEntity.ok("DELETADO");
        }
        return ResponseEntity.notFound().build();
    }

    // create video
    @PostMapping
    private ResponseEntity<VideoDto> createVideo(@RequestBody @Valid VideoForm form, UriComponentsBuilder uriBuilder) {
        Video video = form.toVideo();
        videoService.saveVideo(video);
        URI uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(uri).body(new VideoDto(video));
    }

    // update video by id
    @PutMapping("/{id}")
    private ResponseEntity<VideoDto> updateVideoById(@PathVariable Long id, @RequestBody @Valid VideoPutForm form) {
        Video video = form.updateVideo(id, videoService);        
        if (video != null) {
            videoService.saveVideo(video);
            return ResponseEntity.ok(new VideoDto(video));
        }
        return ResponseEntity.notFound().build();
        
    }

}
