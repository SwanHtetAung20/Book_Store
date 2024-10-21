package com.sha.shopping_books.repositories;

import com.sha.shopping_books.entities.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableScan
public interface UserRepository extends CrudRepository<User,String> {

    Optional<User> findByEmail(String email);
}
