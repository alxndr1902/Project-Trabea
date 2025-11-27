package com.bc46.trabea.auth;


import com.bc46.trabea.auth.dto.AuthLoginRequest;
import com.bc46.trabea.auth.dto.AuthLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthLoginResponse> createToken(@RequestBody AuthLoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }
}
