package com.sha.shopping_books.services;

import com.sha.shopping_books.dtos.AuthRequest;
import com.sha.shopping_books.dtos.AuthResponse;
import com.sha.shopping_books.dtos.RegistrationRequest;
import jakarta.mail.MessagingException;

public interface AuthService {

    void register(RegistrationRequest request) throws MessagingException;

    void activateAccount(String token) throws MessagingException;

    AuthResponse login(AuthRequest request);


}
