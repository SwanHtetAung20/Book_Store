package com.sha.shopping_books.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {

    private String id;
    private String name;
    private String title;
    private String description;
    private String author;
    private String photo;
    private String price;
    private String createdBy;
}
