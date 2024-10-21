package com.sha.shopping_books.dtos;

import com.sha.shopping_books.entities.ROLE;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private ROLE role;
    private String token;
}
