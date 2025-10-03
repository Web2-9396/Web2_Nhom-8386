package com.example.btvn_tuan6.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey jwtSecret;

    @Value("${app.jwt-expiration-ms}")
    private long jwtExpirationMs;

    public JwtTokenProvider(@Value("${app.jwt-secret}") String secretKey) {
        this.jwtSecret = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Sinh token từ email
    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }

    // Lấy email từ token
    public String getEmailFromJWT(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Xác minh token
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("Token đã hết hạn");
        } catch (JwtException ex) {
            throw new RuntimeException("Token không hợp lệ");
        }
    }

    // public ResponseEntity<?> validateToken(String authToken) {
    //     try {
    //         Jwts.parserBuilder()
    //                 .setSigningKey(jwtSecret)
    //                 .build()
    //                 .parseClaimsJws(authToken);
    //         return ResponseEntity.ok("Token hợp lệ");
    //     } catch (ExpiredJwtException ex) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    //                 .body("Token đã hết hạn");
    //     } catch (JwtException ex) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                 .body("Token không hợp lệ");
    //     }
    // }
}
    