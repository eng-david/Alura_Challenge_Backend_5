package br.com.alura.api_videos.api_videos.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.api_videos.api_videos.model.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthTokenService {

    @Value("${jwt.expiration}")
    private String expirationDelay;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {

        AppUser loggedUser = (AppUser) authentication.getPrincipal();
        Date today = new Date();
        Date expiration = new Date(today.getTime() + Long.parseLong(expirationDelay));

        return Jwts.builder()
                .setIssuer("videos app")
                .setSubject(loggedUser.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token){
        return Long.parseLong(Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getSubject());
    }
}
