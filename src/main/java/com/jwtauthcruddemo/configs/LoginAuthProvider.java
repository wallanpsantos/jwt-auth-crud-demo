package com.jwtauthcruddemo.configs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jwtauthcruddemo.dtos.output.UserDto;
import com.jwtauthcruddemo.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
public class LoginAuthProvider {

    private static final int TIME_TO_EXPIRED_TOKEN = 1; // 1 Hour

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final UserService userService;

    public LoginAuthProvider(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto userDto) {
        final var validity = Date.from(Instant.now().plus(TIME_TO_EXPIRED_TOKEN, ChronoUnit.HOURS));
        final var algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withIssuer(userDto.getLogin())
                .withIssuedAt(new Date())
                .withExpiresAt(validity)
                .withClaim("firstName", userDto.getFirstName())
                .withClaim("lastName", userDto.getLastName())
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        final var algorithm = Algorithm.HMAC256(secretKey);

        final JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        final DecodedJWT decodedJWT = jwtVerifier.verify(token);

        final UserDto userDto = getUserDtoFromDecodedJWT(decodedJWT);

        return new UsernamePasswordAuthenticationToken(userDto, null, Collections.emptyList());
    }


    public Authentication validateTokenStrongly(String token) {
        final Algorithm algorithm = Algorithm.HMAC256(secretKey);

        final JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        final DecodedJWT decodedJWT = jwtVerifier.verify(token);

        final UserDto user = userService.findByLogin(decodedJWT.getIssuer());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    /* Usando as informações do JWT para criar um usuario DTO */
    private static UserDto getUserDtoFromDecodedJWT(DecodedJWT decodedJWT) {
        var userDto = new UserDto();
        userDto.setLogin(decodedJWT.getIssuer());
        userDto.setFirstName(decodedJWT.getClaim("firstName").asString());
        userDto.setLastName(decodedJWT.getClaim("lastName").asString());
        return userDto;
    }

}
