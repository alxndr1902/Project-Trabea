package com.bc46.trabea.auth;

import com.bc46.trabea.auth.dto.AuthLoginRequest;
import com.bc46.trabea.auth.dto.AuthLoginResponse;
import com.bc46.trabea.error.exception.UnauthorizedException;
import com.bc46.trabea.error.exception.WorkEmailAndPasswordIncorrectException;
import com.bc46.trabea.jwt.JwtService;
import com.bc46.trabea.role.Role;
import com.bc46.trabea.role.RoleName;
import com.bc46.trabea.user.User;
import com.bc46.trabea.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthLoginResponse login(AuthLoginRequest request) {
        var user = userRepository.findByWorkEmail(request.getWorkEmail())
                .orElseThrow(() -> new WorkEmailAndPasswordIncorrectException("Work Email or Password is incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new WorkEmailAndPasswordIncorrectException("Work Email or password is incorrect");
        }

        if (!hasRole(user, request.getRole())) {
            throw new UnauthorizedException("You are not authorized to login with this role!");
        }
        RoleName role = request.getRole();

        return AuthLoginResponse.builder()
                .token(jwtService.generateToken(user, role))
                .build();
    }

    private boolean hasRole(User user, RoleName roleRequest) {
        return user.getRoles().stream()
                .map(Role::getName)
                .anyMatch(roleRequest::equals);
//              .anyMatch(roleName -> roleName.equals(request.getRole()));
    }

}
