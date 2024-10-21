package com.sha.shopping_books.services;

import com.sha.shopping_books.dtos.PaymentRequest;
import com.sha.shopping_books.dtos.PaymentResponse;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface PaymentService {

    void buyBook(PaymentRequest request) throws IOException;

    List<PaymentResponse> getAllInProgressPayment();

    List<PaymentResponse> getAllDonePayment();

    String cancel(String id) throws MessagingException;

    String accept(String id) throws MessagingException;
}
