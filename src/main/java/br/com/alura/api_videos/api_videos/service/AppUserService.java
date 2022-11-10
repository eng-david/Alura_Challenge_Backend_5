package br.com.alura.api_videos.api_videos.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.alura.api_videos.api_videos.model.AppUser;
import br.com.alura.api_videos.api_videos.model.Authority;

public interface AppUserService {

    Page<AppUser> findAllUsers(Pageable pageable);

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findById(Long id);

    Optional<Authority> findAuthority(String name);

    AppUser saveUser(AppUser user);

    void addAuthorityToUser(AppUser user, Authority authority);

}
