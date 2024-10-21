package com.sha.shopping_books.impls;

import com.cloudinary.Cloudinary;
import com.sha.shopping_books.dtos.PaymentRequest;
import com.sha.shopping_books.dtos.PaymentResponse;
import com.sha.shopping_books.entities.Book;
import com.sha.shopping_books.entities.EmailTemplateName;
import com.sha.shopping_books.entities.Payment;
import com.sha.shopping_books.entities.User;
import com.sha.shopping_books.exception.EntityNotFoundException;
import com.sha.shopping_books.exception.OperationNotPermittedException;
import com.sha.shopping_books.mapper.PaymentMapper;
import com.sha.shopping_books.repositories.BookRepository;
import com.sha.shopping_books.repositories.PaymentRepository;
import com.sha.shopping_books.repositories.UserRepository;
import com.sha.shopping_books.services.PaymentService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static com.sha.shopping_books.entities.EmailTemplateName.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImplementation implements PaymentService {

    private final PaymentRepository repository;
    private final UserRepository userRepository;
    private final PaymentMapper paymentMapper;
    private final EmailService emailService;
    private final BookRepository bookRepository;
    private final Cloudinary cloudinary;
    private final List<String> photoExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", "bmp", "tiff", "tif", "psv", "svg", "webp", "ico", "heic");


    @Override
    public void buyBook(PaymentRequest request) throws IOException {
        var loginUser = loginUser();
        var payment = Payment.builder()
                .bookId(request.bookId())
                .count(request.count())
                .userId(loginUser.getId())
                .paymentBy(request.paymentBy())
                .status(Payment.Status.IN_PROGRESS)
                .photo(savedPhoto(request.file()))
                .price(request.price())
                .createdDate(LocalDateTime.now())
                .build();
        repository.save(payment);
    }

    @Override
    public List<PaymentResponse> getAllInProgressPayment() {
        List<Payment> payments = repository.findAllByStatus(Payment.Status.IN_PROGRESS);
        return payments.stream()
                .map(paymentMapper::toPaymentResponse)
                .toList();
    }

    @Override
    public List<PaymentResponse> getAllDonePayment() {
        List<Payment> payments = repository.findAllByStatus(Payment.Status.DONE);
        return payments.stream()
                .map(paymentMapper::toPaymentResponse)
                .toList();
    }

    @Override
    public String cancel(String id) throws MessagingException {
        repository.findById(id).ifPresent(p -> {
            p.setStatus(Payment.Status.CANCEL);
            repository.save(p);
        });
        var payment = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Internal error. Please contact the system admin.!"));
        var book = bookRepository.findById(payment.getBookId()).orElseThrow(() -> new EntityNotFoundException("Internal error. Please contact the system admin.!"));
        var user = userRepository.findById(payment.getUserId()).orElseThrow(() -> new EntityNotFoundException("Internal error. Please contact the system admin.!"));
        sendEmailForRejectedRequest(user,book,"Reject Request",REQUEST_CANCEL);
        return "Successfully Canceled";
    }

    @Override
    public String accept(String id) throws MessagingException {
        repository.findById(id).ifPresent(p -> {
            p.setStatus(Payment.Status.DONE);
            repository.save(p);
        });
        var payment = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Internal error. Please contact the system admin.!"));
        var book = bookRepository.findById(payment.getBookId()).orElseThrow(() -> new EntityNotFoundException("Internal error. Please contact the system admin.!"));
        var user = userRepository.findById(payment.getUserId()).orElseThrow(() -> new EntityNotFoundException("Internal error. Please contact the system admin.!"));
        sendEmailForRejectedRequest(user,book,"Accept Request",REQUEST_ACCEPT);
        return "Successfully Accepted";
    }


    private void sendEmailForRejectedRequest(User user, Book book, String subject, EmailTemplateName templateName) throws MessagingException {
        emailService.sendEmail(user.getEmail(), user.getName(),
                templateName,
                book.getPhoto(),
                book.getName(),
                subject);
    }

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
