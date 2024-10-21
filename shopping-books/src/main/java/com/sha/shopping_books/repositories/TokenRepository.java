package com.sha.shopping_books.repositories;

import com.sha.shopping_books.entities.Token;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableScan
public interface TokenRepository extends CrudRepository<Token,String> {

    Optional<Token> findByToken(String token);
}
