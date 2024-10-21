package com.sha.shopping_books.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @NotEmpty(message = "name cannot be empty.!")
        @NotNull(message = "name cannot be empty.!")
        String name,
        @NotEmpty(message = "email cannot be empty.!")
        @NotNull(message = "email cannot be empty.!")
        @Email(message = "Email is not well formatted")
        String email,

        @NotEmpty(message = "Password cannot be empty.!")
        @NotNull(message = "Password cannot be empty.!")
        @Size(min = 8, message = "Password should be 8 characters long minimum")
        String password
) {
}
