package com.sha.shopping_books.repositories;

import com.sha.shopping_books.entities.Book;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface BookRepository extends CrudRepository<Book,String> {
}
