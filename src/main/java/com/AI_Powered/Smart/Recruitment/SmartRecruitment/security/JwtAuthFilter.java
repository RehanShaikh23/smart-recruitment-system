package com.AI_Powered.Smart.Recruitment.SmartRecruitment.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {


        System.out.println("=== JWT FILTER DEBUG ===");
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Request Method: " + request.getMethod());

        final String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);

        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No valid Authorization header found - proceeding without authentication");
            System.out.println("========================");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        System.out.println("JWT Token extracted: " + jwt.substring(0, Math.min(jwt.length(), 20)) + "...");

        try {
            userEmail = jwtUtil.getEmail(jwt);
            System.out.println("Email extracted from JWT: " + userEmail);
        } catch (Exception e) {
            System.out.println("Error extracting email from JWT: " + e.getMessage());
            System.out.println("========================");
            filterChain.doFilter(request, response);
            return;
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Loading user details for: " + userEmail);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if (jwtUtil.validate(jwt, userDetails.getUsername())) {
                System.out.println("JWT token is valid - setting authentication");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                       userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Authentication set successfully");
                System.out.println("=== AUTHENTICATION DEBUG ===");
                System.out.println("Authorities: " + userDetails.getAuthorities());
                userDetails.getAuthorities().forEach(authority ->
                        System.out.println("Authority: '" + authority.getAuthority() + "'")
                );
            } else {
                System.out.println("JWT token validation failed");
            }
        } else {
            System.out.println("Either userEmail is null or authentication already exists");
        }

        System.out.println("Final auth status: " + (SecurityContextHolder.getContext().getAuthentication() != null));
        System.out.println("========================");

        filterChain.doFilter(request, response);
    }
}