package com.example.test.filter;


import com.example.test.entity.Student;
import com.example.test.repository.StudentRepository;
import com.example.test.util.Utils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * When request is sent, it comes to MyFilter and based on student's token
 * it checks student from database. If student is found, it puts student in SecurityContextHolder
 * for further use in program
 */
@Component
@RequiredArgsConstructor
public class MyFilter extends OncePerRequestFilter {
    final JwtProvider jwtProvider;
    final StudentRepository userRepository;

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("authorization");
        if (Objects.isNull(authorization) || Objects.equals(authorization, "anonymousUser")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (authorization.startsWith(Utils.BEARER_AUTH)) {
            authorization = authorization.substring(Utils.BEARER_AUTH.length());
            String emailFromToken = jwtProvider.getEmailFromToken(authorization);
            Student user = (Student) userDetailsService.loadUserByUsername(emailFromToken);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    )
            );
        }
        filterChain.doFilter(request, response);
    }
}
