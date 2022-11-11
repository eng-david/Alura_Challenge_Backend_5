package br.com.alura.api_videos.api_videos.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.alura.api_videos.api_videos.service.AuthTokenService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// --- Disabled ---
//@RestController
//@RequestMapping("/auth") 
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final AuthTokenService authTokenService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginForm form){
        UsernamePasswordAuthenticationToken authData = new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(authData);
            String token = authTokenService.generateToken(authentication);
            return ResponseEntity.ok(new TokenDto("", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid username or password");
        }
    }
}

@AllArgsConstructor
@Getter
class LoginForm {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}

@AllArgsConstructor
@Getter
class TokenDto {

    private String type;
    private String token;

}