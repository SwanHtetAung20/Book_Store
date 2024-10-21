package com.sha.shopping_books.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Getter
@AllArgsConstructor
public enum BusinessErrorCodes {


    ACCOUNT_LOCKED(300,FORBIDDEN,"Your account is locked.!"),
    ACCOUNT_DISABLED(301,FORBIDDEN,"Your account is disabled.!"),
    BAD_CREDENTIAL(303,FORBIDDEN,"Provided email or password is incorrect.!");

    private final int code;

    private final HttpStatus httpStatus;

    private final String description;




}
