package br.com.alura.api_videos.api_videos.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoDto {

    private Long id;
    private String titulo;
    private String descricao;
    private String url;

    public VideoDto(Video video) {
        this.id = video.getId();
        this.titulo = video.getTitulo();
        this.descricao = video.getDescricao();
        this.url = video.getUrl();
    }

    public static List<VideoDto> toDto(List<Video> videos) {

        List<VideoDto> videosDto = new ArrayList<>();

        videos.forEach(v -> {
            videosDto.add(new VideoDto(v.getId(), v.getTitulo(), v.getDescricao(), v.getUrl()));
        });

        return videosDto;

        // return videos.stream().map(VideoDto::new).collect(Collectors.toList());

    }
}
