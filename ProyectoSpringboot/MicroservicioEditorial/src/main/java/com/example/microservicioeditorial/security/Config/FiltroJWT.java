package com.example.microservicioeditorial.security.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FiltroJWT extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = getTokenFromRequest(request);

        if(token == null){

            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {

        final String authHead = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHead) && authHead.startsWith("Bearer")){
            return authHead.substring(7);
        }
        return null;
    }
}
/**
 * private final UserDetailsService userDetailsService;
 *     private final servicioJWT servicioJWT;
 *     @Override
 *     protected void doFilterInternal(HttpServletRequest request, // lo que el cliente envia
 *                                     HttpServletResponse response, // lo que nosotros retornamos al cliente
 *                                     FilterChain filterChain) throws ServletException, IOException { // que sigue luego de la autenticacion
 *         final String authHeader = request.getHeader("Authorization");
 *         final String jwt;
 *         final String username;
 *         if(authHeader == null || !authHeader.startsWith("Bearer")){ // Chequeamos que nos envien un jwt
 *             filterChain.doFilter(request,response);
 *             return;
 *         }
 *         jwt = authHeader.substring(7);
 *         username = servicioJWT.getUsername(jwt);
 *         if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){ // si el usuario existe y no estas autenticado
 *             UserDetails userDetails = userDetailsService.loadUserByUsername(username);
 *             if(servicioJWT.validateToken(jwt,userDetails)){
 *                 UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
 *                         userDetails,
 *                         null,
 *                         userDetails.getAuthorities()
 *                 );
 *                 authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
 *                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
 *             }
 *         }
 *         filterChain.doFilter(request,response);
 *     }
 * **/