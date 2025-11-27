package com.bc46.trabea.jwt;

import com.bc46.trabea.role.RoleName;
import com.bc46.trabea.user.User;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String generateToken(User user, RoleName role);

    Boolean isValid(String token, String username);

    Boolean isUsernameValid(String username, String token);

    Boolean isTokenNonExpire(String token);

    Claims getClaims(String token);

    String extractUsername(String token);

    String extractRole(String token);
}
