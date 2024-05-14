package org.hayo.finance.loanbook.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
//    }
//
//
//    public LocalDate getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }
//
//
//    private boolean isTokenExpired(String token) {
//        final LocalDate expiration = getExpirationDateFromToken(token);
//        return expiration.before(LocalDate.now());
//    }
//
//
//    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }
//
//    public String generateToken(String username) {
//        return doGenerateToken(username);
//    }
//
//    private String doGenerateToken(String username) {
//        return jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(LocalDateTime.now())
//                .setExpiration(LocalDateTime.now().plusSeconds(JWT_TOKEN_VALIDITY))
//                .signWith(SignatureAlgorithm.HS512, secret)
//                .compact();
//    }
}
