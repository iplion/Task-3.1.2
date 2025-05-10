package ru.itmentor.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{uuid}")
    public String viewUser(@PathVariable UUID uuid, Model model) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userService.findByUuid(uuid);
        if (user.isPresent() && user.get().getLogin().equals(currentUsername)) {
            model.addAttribute("user", user.get());

            return "user";
        } else {

            return "redirect:/";
        }
    }
}
