package com.sha.shopping_books.mapper;

import com.sha.shopping_books.dtos.BookResponse;
import com.sha.shopping_books.entities.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMapper {


    public BookResponse toBookResponse(Book book){
        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .price(book.getPrice())
                .photo(book.getPhoto())
                .createdBy(book.getCreatedBy())
                .build();
    }
}
