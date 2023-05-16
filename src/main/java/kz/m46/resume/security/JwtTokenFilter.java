package kz.m46.resume.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.m46.resume.models.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtUserContext userContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = jwtUtils.parseAuthorizationToken(request.getHeader("Authorization"));
            if (token != null && jwtUtils.validateToken(token)) {

                Authentication auth = jwtUtils.getAuthentication(token);

                UserDto context = (UserDto) auth.getPrincipal();
                if (!context.getIsActive()) {
                    throw new GeneralSecurityException();
                }

                userContext.setId(context.getId());
                userContext.setEmail(context.getEmail());

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {

            filterChain.doFilter(request, response);
        }
    }
}
