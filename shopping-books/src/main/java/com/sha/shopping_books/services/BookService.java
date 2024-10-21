package com.sha.shopping_books.services;

import com.sha.shopping_books.dtos.BookRequest;
import com.sha.shopping_books.dtos.BookResponse;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

public interface BookService {

    void bookRegistration(BookRequest request) throws IOException;

    List<BookResponse> getAllBooks();

    String delete(String id);

    BookResponse update(String id, @Valid BookRequest request);
}
