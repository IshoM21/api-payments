package com.app.payments.security;

import com.app.payments.domain.model.UserAccount;
import com.app.payments.domain.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticatedUserService {

    private final UserAccountRepository userRepo;

    public UserAccount getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return null; // o lanzar excepción si quieres forzar login
        }

        String email = auth.getName(); // usamos email como username
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found in DB: " + email));
    }
}
