package com.app.payments.presentation.controller;

import com.app.payments.domain.model.UserAccount;
import com.app.payments.domain.model.dto.auth.AuthResponse;
import com.app.payments.domain.model.dto.auth.LoginRequest;
import com.app.payments.domain.model.dto.auth.RegisterRequest;
import com.app.payments.domain.model.enums.UserRole;
import com.app.payments.domain.repository.UserAccountRepository;
import com.app.payments.security.CustomerUserDetails;
import com.app.payments.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserAccountRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(AuthResponse.builder()
                            .accessToken("Email already in use")
                            .tokenType("error")
                            .build());
        }

        UserAccount user = UserAccount.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(UserRole.ROLE_USER))
                .build();

        userRepo.save(user);

        // Autologin opcional: generamos token directo
        CustomerUserDetails userDetails = new CustomerUserDetails(user);
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .accessToken(token)
                        .tokenType("Bearer")
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        CustomerUserDetails userDetails = (CustomerUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .accessToken(token)
                        .tokenType("Bearer")
                        .build()
        );
    }
}
