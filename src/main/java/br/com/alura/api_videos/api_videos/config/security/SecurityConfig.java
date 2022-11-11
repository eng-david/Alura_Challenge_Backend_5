package br.com.alura.api_videos.api_videos.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.api_videos.api_videos.filter.AuthenticationFilter;
import br.com.alura.api_videos.api_videos.filter.CustomAuthenticationFilter;
import br.com.alura.api_videos.api_videos.service.AppUserServiceImp;
import br.com.alura.api_videos.api_videos.service.AuthTokenService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppUserServiceImp userService;
    private final AuthTokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.expiration}")
    private String expirationDelay;

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManager authenticationManager = authenticationManager(http);

        return http
                .csrf().disable()
                .authorizeRequests()
                // .antMatchers("/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/categorias/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/categorias/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/videos/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/videos/**").hasRole("USER")
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(new CustomAuthenticationFilter(authenticationManager, tokenService))
                .addFilterBefore(new AuthenticationFilter(tokenService, userService),
                        UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager)
                .build();

    }

    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

}
