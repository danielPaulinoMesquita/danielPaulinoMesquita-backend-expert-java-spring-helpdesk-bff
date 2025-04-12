package br.com.daniel.helpdeskbff.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.List;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTUtil jwtUtil;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        final String header = request.getHeader("Authorization");

        if (header == null ) {
            chain.doFilter(request, response);
            return;
        }

        if (header.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);

            if(authenticationToken != null) {
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        } else {
            chain.doFilter(request, response);
            return;
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        String userName = jwtUtil.getUsername(token);
        Claims claims = jwtUtil.getClaims(token);
        List<GrantedAuthority> authorities =  jwtUtil.getAuthorities(claims);

        if (userName != null) {
            return new UsernamePasswordAuthenticationToken(userName, null, authorities);
        }

        return null;
    }
}
