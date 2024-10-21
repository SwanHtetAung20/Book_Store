package com.sha.shopping_books.dtos;


import com.sha.shopping_books.entities.Payment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

public record PaymentRequest(

        String id,
        @NotNull(message = "Book cannot be empty.!")
        @NotEmpty(message = "Book cannot be empty.!")
        String bookId,
        @Positive(message = "Please choose one or more books")
        @NotEmpty(message = "Please enter the count you want to buy.!")
        String count,
        @NotNull(message = "Price cannot be empty.!")
        @NotEmpty(message = "Price cannot be empty.!")
        String price,
        @NotNull(message = "Please choose the payment method")
        Payment.PaymentBy paymentBy,
        MultipartFile file

) {
}
