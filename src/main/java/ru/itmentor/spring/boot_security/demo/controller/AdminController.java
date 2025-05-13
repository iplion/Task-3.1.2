package ru.itmentor.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{uuid}")
    public ResponseEntity<User> getUser(@PathVariable UUID uuid) {

        return userService.findByUuid(uuid)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> newUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @DeleteMapping("/users/{uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID uuid) {
        return userService.findByUuid(uuid)
            .map(user -> {
                userService.deleteByUuid(uuid);
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{uuid}")
    public ResponseEntity<User> updateUser(@PathVariable UUID uuid, @RequestBody User editedUser) {
        return userService.findByUuid(uuid)
            .map(user -> {
                editedUser.setPassword(
                    (editedUser.getPassword() == null || editedUser.getPassword().isEmpty())
                        ? user.getPassword()
                        : userService.encodePassword(editedUser.getPassword())
                );

                return ResponseEntity.ok(userService.save(editedUser));
            })
            .orElse(ResponseEntity.notFound().build());
    }

}
