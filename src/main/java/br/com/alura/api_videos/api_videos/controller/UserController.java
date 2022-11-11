package br.com.alura.api_videos.api_videos.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.api_videos.api_videos.dto.AppUserDto;
import br.com.alura.api_videos.api_videos.model.AppUser;
import br.com.alura.api_videos.api_videos.model.Authority;
import br.com.alura.api_videos.api_videos.service.AppUserServiceImp;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Transactional
public class UserController {

    private final AppUserServiceImp userService;

    @GetMapping
    public ResponseEntity<Page<AppUserDto>> getUsers(Pageable pageable) {
        Page<AppUser> users = userService.findAllUsers(pageable);
        return ResponseEntity.ok(userService.toListDto(users));
    }

    @GetMapping("/")
    public ResponseEntity<?> getUserByUsername(String username) {
        Optional<AppUser> user = userService.findByUsername(username);
        if (user.isPresent())
            return ResponseEntity.ok(userService.toDto(user.get()));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
    }

    @PostMapping
    public ResponseEntity<AppUserDto> saveUser(@RequestBody @Valid AppUser user, UriComponentsBuilder uriBuilder) {
        userService.saveUser(user);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(userService.toDto(user));
    }

    @PostMapping("/authoritytouser")
    public ResponseEntity<AppUserDto> addAuthorityToUser(@RequestBody @Valid AuthorityToUserForm form) {
        Optional<AppUser> user = userService.findByUsername(form.getUsername());
        Optional<Authority> authority = userService.findAuthority(form.getAuthority());
        if (user.isPresent() & authority.isPresent()) {
            if (!user.get().getAuthorities().contains(authority.get()))
                user.get().getAuthorities().add(authority.get());
            return ResponseEntity.ok(userService.toDto(user.get()));
        } else
            return ResponseEntity.notFound().build();
    }

}

@Data
class AuthorityToUserForm {
    @NotBlank
    private String username;
    @NotBlank
    private String authority;
}
