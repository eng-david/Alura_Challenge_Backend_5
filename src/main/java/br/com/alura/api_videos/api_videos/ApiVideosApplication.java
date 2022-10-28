package br.com.alura.api_videos.api_videos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.alura.api_videos.api_videos.model.Video;
import br.com.alura.api_videos.api_videos.service.VideoService;

@SpringBootApplication
public class ApiVideosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiVideosApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(VideoService videoService) {

		return args -> {
			videoService.saveVideo(new Video(null, "titulo 1", "descricao 1", "http://www.voltx.com.br"));
			videoService.saveVideo(new Video(null, "titulo 2", "descricao 2", "http://www.voltx.com.br"));
			videoService.saveVideo(new Video(null, "titulo 3", "descricao 3", "http://www.voltx.com.br"));
			videoService.saveVideo(new Video(null, "titulo 4", "descricao 4", "http://www.voltx.com.br"));
			videoService.saveVideo(new Video(null, "titulo 5", "descricao 5", "http://www.voltx.com.br"));
		};

	}

}
