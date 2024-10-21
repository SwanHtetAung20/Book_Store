package com.sha.shopping_books.impls;

import com.sha.shopping_books.entities.ROLE;
import com.sha.shopping_books.entities.User;
import com.sha.shopping_books.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInitialization {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @EventListener(ContextRefreshedEvent.class)
    public void initializedUser(){
        if(repository.count() == 0){
            var user = User.builder()
                    .name("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin@123"))
                    .role(ROLE.ADMIN)
                    .accountLocked(false)
                    .isActive(true)
                    .build();
            repository.save(user);
        }
    }
}
