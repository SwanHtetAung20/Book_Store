package com.sha.shopping_books.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account"),

    REQUEST_CANCEL("request_cancel"),

    REQUEST_ACCEPT("request_accept");

    private final String name;

}
