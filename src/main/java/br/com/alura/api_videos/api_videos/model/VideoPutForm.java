package br.com.alura.api_videos.api_videos.model;

import java.util.Optional;

import org.hibernate.validator.constraints.URL;

import br.com.alura.api_videos.api_videos.service.VideoService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VideoPutForm {

    private String titulo;
    private String descricao;
    @URL
    private String url;

    public Video updateVideo(Long id, VideoService videoService) {
        Optional<Video> video = videoService.findById(id);
        if (video.isPresent()) {
            if (this.titulo != null) video.get().setTitulo(this.titulo);
            if (this.descricao != null) video.get().setDescricao(this.descricao);
            if (this.url != null) video.get().setUrl(this.url);
            return video.get();
        }
        
        return null;
    }
}
