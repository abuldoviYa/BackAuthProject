package com.abuldovi.backauthproject.config;

import com.abuldovi.backauthproject.dto.UserDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;


@RequiredArgsConstructor
@Component
public class UserAuthProvider {
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDTO userDTO){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3_600_000);
        return JWT.create()
                .withIssuer(userDTO.getLogin())
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .withClaim("firstName", userDTO.getFirstName())
                .withClaim("lastName", userDTO.getLastName())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        UserDTO user = UserDTO.builder()
                .login(decodedJWT.getIssuer())
                .firstName(decodedJWT.getClaim("firstName").asString())
                .lastName(decodedJWT.getClaim("lastName").asString())
                .build();
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
