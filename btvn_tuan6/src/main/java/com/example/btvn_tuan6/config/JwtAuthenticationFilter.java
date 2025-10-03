package com.example.btvn_tuan6.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (tokenProvider.validateToken(token)) {
                String username = tokenProvider.getEmailFromJWT(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);

    }

}

// protected void doFilterInternal(HttpServletRequest request,
//                                 HttpServletResponse response,
//                                 FilterChain filterChain) throws ServletException, IOException {
//     String header = request.getHeader("Authorization");
//     // Kiểm tra nếu header tồn tại và bắt đầu bằng "Bearer"
//     if (header != null && header.startsWith("Bearer ")) {
//         String token = header.substring(7);
//         // Kiểm tra token hợp lệ thông qua validateToken
//         ResponseEntity<?> tokenValidationResponse = tokenProvider.validateToken(token);
//         // Nếu token không hợp lệ (không phải 200 OK), trả về phản hồi lỗi
//         if (tokenValidationResponse.getStatusCode() != HttpStatus.OK) {
//             response.setStatus(tokenValidationResponse.getStatusCodeValue());  // Set status code từ validateToken
//             response.getWriter().write((String) tokenValidationResponse.getBody()); // Ghi thông báo lỗi vào response
//             return; // Dừng tiếp tục filter chain
//         }
//         // Token hợp lệ, tiếp tục xử lý thông tin người dùng
//         String username = tokenProvider.getEmailFromJWT(token);
//         UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//         UsernamePasswordAuthenticationToken authentication =
//                 new UsernamePasswordAuthenticationToken(
//                         userDetails,
//                         null,
//                         userDetails.getAuthorities()
//                 );
//         SecurityContextHolder.getContext().setAuthentication(authentication);
//     }
//     filterChain.doFilter(request, response); // Tiếp tục filter chain nếu token hợp lệ
// }
