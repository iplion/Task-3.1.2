package ru.itmentor.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{uuid}")
    public ResponseEntity<User> viewUser(@PathVariable UUID uuid) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userService.findByUuid(uuid);
        if (user.isPresent() && user.get().getLogin().equals(currentUsername)) {

            return ResponseEntity.ok(user.get());
        } else {

            return ResponseEntity.notFound().build();
        }
    }
}
