package com.sha.shopping_books.repositories;

import com.sha.shopping_books.entities.Payment;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableScan
public interface PaymentRepository extends CrudRepository<Payment,String> {

    List<Payment> findAllByStatus(Payment.Status status);
}
