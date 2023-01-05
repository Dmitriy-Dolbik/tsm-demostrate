package ru.karod.tsm.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.karod.tsm.models.User;
import ru.karod.tsm.services.impl.TsmCustomUserDetailsServiceImpl;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTTokenProvider jwtTokenProvider;
    private final TsmCustomUserDetailsServiceImpl tsmCustomUserDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED
                        , "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    String userId = jwtTokenProvider.getUserIdFromToken(jwt);
                    User userDetails = tsmCustomUserDetailsServiceImpl.loadUserById(userId);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails
                                    , userDetails.getPassword()
                                    , userDetails.getAuthorities());
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException exs) {
                    log.error("Could not set user authentication");
                    exs.printStackTrace();

                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
