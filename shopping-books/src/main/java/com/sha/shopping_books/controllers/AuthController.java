package com.sha.shopping_books.controllers;

import com.sha.shopping_books.dtos.AuthRequest;
import com.sha.shopping_books.dtos.AuthResponse;
import com.sha.shopping_books.dtos.RegistrationRequest;
import com.sha.shopping_books.services.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
        authService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody @Valid AuthRequest request){
      return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/activate-account")
    public void confirm(@RequestParam(name = "token") String token) throws MessagingException {
        authService.activateAccount(token);
    }

}
