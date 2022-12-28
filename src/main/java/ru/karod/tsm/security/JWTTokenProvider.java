package ru.karod.tsm.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.karod.tsm.models.User;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JWTTokenProvider {
    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        UUID userId = user.getId();
        return JWT.create()
                .withSubject("User details")
                .withClaim("id", userId.toString())
                .withClaim("email", user.getEmail())
                .withIssuedAt(new Date())
                .withIssuer("tsm")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String getUserIdFromToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("tsm")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        String userId = jwt.getClaim("id").asString();
        return userId;
    }
}
