package com.hireboost.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    public String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000)) // сутки
                .sign(Algorithm.HMAC256(jwtSigningKey));
    }

    public String generateTokenWithClaims(UserDetails userDetails, Map<String, String> extraClaims) {
        var jwtBuilder = JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000));

        extraClaims.forEach(jwtBuilder::withClaim);

        return jwtBuilder.sign(Algorithm.HMAC256(jwtSigningKey));
    }

    public String extractUserName(String token) {
        return getDecodedJWT(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getDecodedJWT(token).getExpiresAt();
        return expiration.before(new Date());
    }

    private DecodedJWT getDecodedJWT(String token) {
        return JWT.require(Algorithm.HMAC256(jwtSigningKey))
                .build()
                .verify(token);
    }
}
