package com.sha.shopping_books.impls;

import com.cloudinary.Cloudinary;
import com.sha.shopping_books.dtos.BookRequest;
import com.sha.shopping_books.dtos.BookResponse;
import com.sha.shopping_books.entities.Book;
import com.sha.shopping_books.entities.User;
import com.sha.shopping_books.exception.EntityNotFoundException;
import com.sha.shopping_books.exception.OperationNotPermittedException;
import com.sha.shopping_books.mapper.BookMapper;
import com.sha.shopping_books.repositories.BookRepository;
import com.sha.shopping_books.repositories.UserRepository;
import com.sha.shopping_books.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {

    private final BookRepository repository;
    private final UserRepository userRepository;
    private final BookMapper bookMapper;
    private final Cloudinary cloudinary;
    private final List<String> photoExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", "bmp", "tiff", "tif", "psv", "svg", "webp", "ico", "heic");


    @Override
    public void bookRegistration(BookRequest request) throws IOException {
        var user = loginUser();
        var book = Book.builder()
                .name(request.name())
                .title(request.title())
                .description(request.description())
                .author(request.author())
                .createdBy(user.getId())
                .photo(savedPhoto(request.photo()))
                .price(request.price())
                .status(Book.Status.PUBLIC)
                .build();
        repository.save(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        List<Book> books = (List<Book>) repository.findAll();
        return books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
    }

    @Override
    public String delete(String id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Requested book doesn't exist.!"));
        repository.deleteById(id);
        return "Successfully Deleted";
    }

    @Override
    public BookResponse update(String id, BookRequest request) {
        return repository.findById(id)
                .map(book -> {
                    book.setName(request.name());
                    book.setTitle(request.title());
                    book.setDescription(request.description());
                    book.setAuthor(request.author());
                    book.setPrice(request.price());

                    if (request.photo() != null && !request.photo().isEmpty()) {
                        try {
                            book.setPhoto(savedPhoto(request.photo()));
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to save photo", e);
                        }
                    }
                    return repository.save(book);
                })
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("Requested book doesn't exist.!"));
    }


    @Async
    private String savedPhoto(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return null;
        }

        if (!isValidPhotoExtension(getFileExtension(file))) {
            throw new OperationNotPermittedException("Photo Error! Please try another photo.!");
        }
        String url = uploadPhoto(file);
        System.out.println("URL " + url);
        return url;
    }

    private String uploadPhoto(MultipartFile file) throws IOException {
        return cloudinary.uploader()
                .upload(file.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                .get("url").toString();
    }

    private boolean isValidPhotoExtension(String extension) {
        return photoExtensions.contains(extension);
    }

    private String getFileExtension(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.')).toLowerCase();
    }

    private User loginUser() {
        var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("Please Login to buy books.!"));
    }
}
