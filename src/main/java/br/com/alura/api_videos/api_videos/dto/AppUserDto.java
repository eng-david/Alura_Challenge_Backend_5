package br.com.alura.api_videos.api_videos.dto;

import java.util.ArrayList;
import java.util.Collection;
import br.com.alura.api_videos.api_videos.model.AppUser;
import br.com.alura.api_videos.api_videos.model.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AppUserDto {

    private String name;
    private String username;
    private Collection<Authority> authorities = new ArrayList<>();

    public AppUserDto(AppUser user) {
        this.name = user.getName();
        this.username = user.getUsername();
        this.authorities = user.getAuthorities();

    }
}