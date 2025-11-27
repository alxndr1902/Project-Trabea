package com.bc46.trabea.jwt;

import com.bc46.trabea.auth.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailService userDetailService;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractTokenFromRequest(request);

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            String username = jwtService.extractUsername(token);
            var authentication = SecurityContextHolder.getContext().getAuthentication();

            if (username != null && authentication == null) {
                authenticateUser(request, token, username);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT catch error: {}", e.getMessage());
            authenticationFailureHandler.onAuthenticationFailure(request, response, new BadCredentialsException("Invalid JWT Token or Token Is Expired"));
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public void authenticateUser(HttpServletRequest request, String token, String username) {
        var userDetails = userDetailService.loadUserByUsername(username);

        if (jwtService.isValid(token, userDetails.getUsername())) {
            String roleFromToken = jwtService.extractRole(token);

            Collection<? extends GrantedAuthority> authorities =
                    Collections.singletonList(new SimpleGrantedAuthority(roleFromToken));

            var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
