package com.sha.shopping_books.controllers;

import com.sha.shopping_books.dtos.BookRequest;
import com.sha.shopping_books.dtos.BookResponse;
import com.sha.shopping_books.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/books")
public class BookController {

    private final BookService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<?> bookRegistrationHandler(@ModelAttribute @Valid BookRequest request) throws IOException {
        System.out.println(request);
        service.bookRegistration(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAllBooks(){
        return ResponseEntity.ok(service.getAllBooks());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHandler(@PathVariable("id")String id){
        return ResponseEntity.ok(service.delete(id));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<BookResponse> editHandler(@PathVariable("id")String id,@ModelAttribute @Valid BookRequest request){
        return ResponseEntity.ok(service.update(id,request));
    }
}
