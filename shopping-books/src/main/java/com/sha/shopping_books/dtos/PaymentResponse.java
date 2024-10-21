package com.sha.shopping_books.dtos;

import com.sha.shopping_books.entities.Payment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private String id;

    private String bookId;

    private String userId;

    private String count;

    private String photo;

    private String price;

    private Payment.PaymentBy paymentBy;

    private Payment.Status status;

    private LocalDateTime createdDate;
}
