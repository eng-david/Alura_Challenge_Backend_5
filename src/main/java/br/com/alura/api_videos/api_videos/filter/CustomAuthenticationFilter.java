package br.com.alura.api_videos.api_videos.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.api_videos.api_videos.service.AuthTokenService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AuthTokenService authTokenService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        
        try {
            LoginForm form = new ObjectMapper().readValue(request.getInputStream(), LoginForm.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    form.getUsername(), form.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        } catch (BadCredentialsException e) {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request, response));
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        log.info("successful authentication");
        String token = authTokenService.generateToken(authResult);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        TokenDto tokenDto = new TokenDto("Bearer", token);
        new ObjectMapper().writeValue(response.getOutputStream(), tokenDto);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        log.info("failed authentication");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Invalid username or password");
        response.getWriter().flush();
    }
}

@NoArgsConstructor
@Data
class LoginForm {

    private String username;
    private String password;

}

@AllArgsConstructor
@Getter
class TokenDto {

    private String type;
    private String token;

}