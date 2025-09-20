    package com.example.bttuan3;

    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.SignatureAlgorithm;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Component;

    import java.nio.charset.StandardCharsets;
    import java.security.Key;
    import java.util.Date;

    @Component
    public class JwtUtil {

        // Nên dùng chuỗi dài, random, đủ mạnh
      private static final String SECRET_KEY = "my_super_secure_random_secret_key_which_is_long_enough_and_more_1234567890";


        private static final long EXPIRATION_TIME = 86400000; // 1 ngày (ms)

        private Key getSigningKey() {
            return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        }

        // Tạo token từ email
        public String generateToken(String email) {
            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();
        }

        // Lấy email từ token
        public String getEmailFromToken(String token) {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }

        // Kiểm tra token hợp lệ
        public boolean validateToken(String token, UserDetails userDetails) {
            String email = getEmailFromToken(token);
            return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
        }

        // Kiểm tra token hết hạn
        private boolean isTokenExpired(String token) {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().before(new Date());
        }
    }
