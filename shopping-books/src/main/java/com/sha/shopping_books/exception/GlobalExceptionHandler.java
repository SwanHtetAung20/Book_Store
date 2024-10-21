package com.sha.shopping_books.exception;

import com.sha.shopping_books.dtos.ExceptionResponse;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static com.sha.shopping_books.dtos.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {


    @ExceptionHandler(value = LockedException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(LockedException exp){
       return ResponseEntity.status(UNAUTHORIZED).body(
               ExceptionResponse.builder()
                       .businessErrorCode(ACCOUNT_LOCKED.getCode())
                       .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                       .error(exp.getMessage())
                       .build()
       );
    }

    @ExceptionHandler(value = DisabledException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(DisabledException exp){
        return ResponseEntity.status(UNAUTHORIZED).body(
                ExceptionResponse.builder()
                        .businessErrorCode(ACCOUNT_DISABLED.getCode())
                        .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(BadCredentialsException exp){
        return ResponseEntity.status(UNAUTHORIZED).body(
                ExceptionResponse.builder()
                        .businessErrorCode(BAD_CREDENTIAL.getCode())
                        .businessErrorDescription(BAD_CREDENTIAL.getDescription())
                        .error(BAD_CREDENTIAL.getDescription())
                        .build()
        );
    }

    @ExceptionHandler(value = ActivationTokenException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(ActivationTokenException exp){
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(EntityNotFoundException exp){
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = MessagingException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(MessagingException exp){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(MethodArgumentNotValidException exp){
        Set<String> errors = new HashSet<>();
        for(ObjectError error: exp.getBindingResult().getAllErrors()){
            var errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        }
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .validationErrors(errors)
                        .build()
        );
    }

    @ExceptionHandler(value = OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(OperationNotPermittedException exp){
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception exp){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .businessErrorDescription("Internal error, Please contact the admin.!")
                        .error(exp.getMessage())
                        .build()
        );
    }



}
