package ru.karod.tsm.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

        String userId = user.getId();
        return JWT.create()
                .withSubject("User details")
                .withClaim("id", userId)
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

        DecodedJWT decodedJwt = verifier.verify(token);

        System.out.println("Header =  " + decodedJwt.getHeader());
        System.out.println("Algorithm =  " + decodedJwt.getAlgorithm());
        System.out.println("Audience =  " + decodedJwt.getAudience());
        decodedJwt.getClaims().forEach((k, v) -> {
            System.out.println("Claim " + k + " = " + v.asString());
        });
        System.out.println("ContentType =  " + decodedJwt.getContentType());
        System.out.println("ExpiresAt =  " + decodedJwt.getExpiresAt());
        System.out.println("Id =  " + decodedJwt.getId());
        System.out.println("Issuer =  " + decodedJwt.getIssuer());
        System.out.println("Subject =  " + decodedJwt.getSubject());

        String userId = decodedJwt.getClaim("id").asString();
        return userId;
    }
}
