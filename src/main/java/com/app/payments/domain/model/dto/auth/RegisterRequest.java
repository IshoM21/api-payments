package com.app.payments.domain.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(max = 120)
    private String fullName;

    @NotBlank
    @Size(min = 6, max = 64)
    private String password;
}
