package com.bc46.trabea.jwt;

import com.bc46.trabea.role.RoleName;
import com.bc46.trabea.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtServiceImpl implements JwtService {
    private final String secretKey = "cihuyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy";

    @Override
    public String generateToken(User user, RoleName role) {
        var claimsMap = new HashMap<String, Object>();
        claimsMap.put("role", "ROLE_" + role.name());
        claimsMap.put("username", user.getWorkEmail());

        return Jwts.builder()
                .subject(user.getWorkEmail())
                .issuer("Project Liber")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
                .claims(claimsMap)
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public Boolean isValid(String token, String username) {
        return isUsernameValid(username, token) && isTokenNonExpire(token);
    }

    @Override
    public Boolean isUsernameValid(String username, String token) {
        return getClaims(token).getSubject().equals(username);
    }

    @Override
    public Boolean isTokenNonExpire(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }
}
