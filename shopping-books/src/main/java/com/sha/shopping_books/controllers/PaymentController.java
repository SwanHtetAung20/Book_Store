package com.sha.shopping_books.controllers;

import com.sha.shopping_books.dtos.PaymentRequest;
import com.sha.shopping_books.dtos.PaymentResponse;
import com.sha.shopping_books.services.PaymentService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/payments")
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    @ResponseStatus(ACCEPTED)
    public void savePayment(@ModelAttribute @Valid PaymentRequest request) throws IOException {
        service.buyBook(request);
    }

    @GetMapping("/in-progress")
    public ResponseEntity<List<PaymentResponse>> getAllInProgressPayments(){
        return ResponseEntity.ok(service.getAllInProgressPayment());
    }

    @GetMapping("/done")
    public ResponseEntity<List<PaymentResponse>> getAllDonePayments(){
        return ResponseEntity.ok(service.getAllDonePayment());
    }

    @GetMapping("/request-cancel/{id}")
    public ResponseEntity<String> requestCancel(@PathVariable("id")String id) throws MessagingException {
        return ResponseEntity.ok(service.cancel(id));
    }

    @GetMapping("/request-accept/{id}")
    public ResponseEntity<String> requestAccept(@PathVariable("id")String id) throws MessagingException {
        return ResponseEntity.ok(service.accept(id));
    }
}
