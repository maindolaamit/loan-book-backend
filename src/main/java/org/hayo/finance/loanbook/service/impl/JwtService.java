package org.hayo.finance.loanbook.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.dto.request.UserLoginRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    JwtService(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token).getSubject();
    }

    public LocalDateTime getExpirationDateFromToken(String token) {
        val expiration = getClaimFromToken(token).getExpiration();
        return expiration.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
    }

    public boolean isTokenExpired(String token) {
        final LocalDateTime expiration = getExpirationDateFromToken(token);
        return expiration.isBefore(LocalDateTime.now());
    }

    private Claims getClaimFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String generateToken(String username) {
        return doGenerateToken(username);
    }

    private String doGenerateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public void authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public String login(@NotNull UserLoginRequest request, UserService userService) {
        log.info("Logging in user: {}", request.email());
        authenticate(request.email(), request.password());
        return userService.saveToken(request.email(), generateToken(request.email()));
    }

    public String regenerateToken(String email) {
        return userService.saveToken(email, generateToken(email));
    }
}
