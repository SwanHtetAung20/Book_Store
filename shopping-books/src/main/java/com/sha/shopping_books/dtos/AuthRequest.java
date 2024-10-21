package com.sha.shopping_books.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @Email(message = "Email is not well formatted")
        @NotEmpty(message = "Email cannot be empty.!")
        @NotNull(message = "Email cannot be empty.!")
        String email,
        @NotEmpty(message = "Password cannot be empty.!")
        @NotNull(message = "Password cannot be empty.!")
        @Size(min = 8, message = "Password should be 8 characters long minimum")
        String password
) {
}
