package com.example.userservice.repository;

import com.example.userservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_returnsUser() {
        User user = User.builder()
                .name("Charlie")
                .email("charlie@example.com")
                .phone("+1234567892")
                .totalOrders(0)
                .totalSpent(0.0)
                .build();
        userRepository.save(user);
        Optional<User> found = userRepository.findByEmail("charlie@example.com");
        assertTrue(found.isPresent());
        assertEquals("Charlie", found.get().getName());
    }

    @Test
    void findByEmail_notFound() {
        Optional<User> found = userRepository.findByEmail("notfound@example.com");
        assertTrue(found.isEmpty());
    }
}
