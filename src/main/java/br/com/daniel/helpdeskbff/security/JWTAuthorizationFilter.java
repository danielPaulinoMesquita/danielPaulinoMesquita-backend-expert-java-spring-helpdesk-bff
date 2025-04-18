package br.com.daniel.helpdeskbff.security;

import br.com.userservice.commonslib.model.exceptions.StandardError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTUtil jwtUtil;
    private final String[] publicRoutes; // this is used for checking the urls and allowing them to move forward

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, String[] publicRoutes) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.publicRoutes = publicRoutes;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        if (isPublicRoute(request.getRequestURI())) {
            chain.doFilter(request, response); // if it's public, it is going to move forward without the following authentication
            return;
        }

        final String header = request.getHeader("Authorization");

        if (header == null ) {
            handleException(request.getRequestURI(), "Authorization header is missing", response);
            return;
        }

        if (header.startsWith("Bearer ")) {
            try {
                UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);

                if (authenticationToken != null) {
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                handleException(request.getRequestURI(), e.getMessage(), response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private void handleException(String requestURI,
                                 String authorizationHeaderIsMissing,
                                 HttpServletResponse response) throws IOException {

        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(authorizationHeaderIsMissing)
                .path(requestURI)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = objectMapper.writeValueAsString(error);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }

    private boolean isPublicRoute(String route) {
        for (String publicRoute : publicRoutes) {
            if (route.startsWith(publicRoute)) {
                return true;
            }
        }

        return false;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTHORIZATION).substring(7);

        String username = jwtUtil.getUsername(token);
        Claims claims = jwtUtil.getClaims(token);
        List<GrantedAuthority> authorities = jwtUtil.getAuthorities(claims);

        return username != null ? new UsernamePasswordAuthenticationToken(username, null, authorities) : null;
    }
}
