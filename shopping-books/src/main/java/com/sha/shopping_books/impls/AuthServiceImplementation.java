package com.sha.shopping_books.impls;

import com.sha.shopping_books.dtos.AuthRequest;
import com.sha.shopping_books.dtos.AuthResponse;
import com.sha.shopping_books.dtos.RegistrationRequest;
import com.sha.shopping_books.entities.ROLE;
import com.sha.shopping_books.entities.Token;
import com.sha.shopping_books.entities.User;
import com.sha.shopping_books.exception.ActivationTokenException;
import com.sha.shopping_books.exception.EntityNotFoundException;
import com.sha.shopping_books.exception.OperationNotPermittedException;
import com.sha.shopping_books.repositories.TokenRepository;
import com.sha.shopping_books.repositories.UserRepository;
import com.sha.shopping_books.services.AuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

import static com.sha.shopping_books.entities.EmailTemplateName.ACTIVATE_ACCOUNT;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${application.mailing.activate-url}")
    private String activationUrl;


    @Override
    public void register(RegistrationRequest request) throws MessagingException {
        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(ROLE.USER)
                .accountLocked(false)
                .isActive(false)
                .build();
        repository.save(user);
        sendEmailForAccountValidation(user);
    }

    @Override
    public void activateAccount(String token) throws MessagingException {
        var svgToken = tokenRepository.findByToken(token).orElseThrow(() -> new ActivationTokenException("Invalid token.!"));
        var user = repository.findById(svgToken.getUserId()).orElseThrow(() -> new EntityNotFoundException("User Not Found.!"));
        if (LocalDateTime.now().isAfter(svgToken.getExpiredAt())) {
            sendEmailForAccountValidation(user);
            throw new ActivationTokenException("Activation token has just expired.!, We just sent another activation token to your email address.!");
        }
        user.setActive(true);
        repository.save(user);
        svgToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(svgToken);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = ((User) auth.getPrincipal());
        var jwtToken = jwtService.generateToken(new HashMap<>(), user);
        return AuthResponse.builder()
                .role(user.getRole())
                .token(jwtToken)
                .build();
    }

    private void sendEmailForAccountValidation(User user) throws MessagingException {
        String token = generateAndSaveActivationToken(user);
        emailService.sendEmail(user.getEmail(), user.getName(),
                ACTIVATE_ACCOUNT,
                activationUrl,
                token,
                "Account Activation");
    }


    private String generateAndSaveActivationToken(User user) {
        String token = generateActivationCode(6);
        var svgToken = Token.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(2))
                .userId(user.getId())
                .build();
        tokenRepository.save(svgToken);
        return token;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder builder = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomNumber = random.nextInt(characters.length());
            builder.append(characters.charAt(randomNumber));
        }
        return builder.toString();
    }
}
