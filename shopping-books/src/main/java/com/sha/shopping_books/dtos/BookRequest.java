package com.sha.shopping_books.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record BookRequest(

        String id,
        @NotNull(message = "name cannot be empty.!")
        @NotEmpty(message = "name cannot be empty.!")
        String name,
        @NotNull(message = "title cannot be empty.!")
        @NotEmpty(message = "title cannot be empty.!")
        String title,
        @NotNull(message = "description cannot be empty.!")
        @NotEmpty(message = "description cannot be empty.!")
        String description,
        @NotNull(message = "author cannot be empty.!")
        @NotEmpty(message = "author cannot be empty.!")
        String author,
        @NotNull(message = "price cannot be empty.!")
        @NotEmpty(message = "price cannot be empty.!")
        String price,
        MultipartFile photo
) {
}
