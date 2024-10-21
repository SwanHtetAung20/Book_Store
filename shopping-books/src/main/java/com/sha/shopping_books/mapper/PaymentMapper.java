package com.sha.shopping_books.mapper;

import com.sha.shopping_books.dtos.PaymentResponse;
import com.sha.shopping_books.entities.Payment;
import com.sha.shopping_books.exception.EntityNotFoundException;
import com.sha.shopping_books.repositories.BookRepository;
import com.sha.shopping_books.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentMapper {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public PaymentResponse toPaymentResponse(Payment payment){
        var book = bookRepository.findById(payment.getBookId()).orElseThrow(() -> new EntityNotFoundException("Internal Errors,Please contact the system admin.!"));
        var user = userRepository.findById(payment.getUserId()).orElseThrow(() -> new EntityNotFoundException("Internal Errors,Please contact the system admin.!"));
        return PaymentResponse.builder()
                .id(payment.getId())
                .userId(user.getName())
                .bookId(book.getName())
                .count(payment.getCount())
                .photo(payment.getPhoto())
                .paymentBy(payment.getPaymentBy())
                .status(payment.getStatus())
                .price(payment.getPrice())
                .createdDate(payment.getCreatedDate())
                .build();
    }
}
