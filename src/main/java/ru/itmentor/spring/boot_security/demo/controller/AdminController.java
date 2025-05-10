package ru.itmentor.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final String defaultRedirectPage = "redirect:/admin/users";

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());

        return "users";
    }

    @GetMapping("/users/edit")
    public String createNewUser(Model model) {
        model.addAttribute("user", new User());

        return "userEdit";
    }

    @GetMapping("/users/edit/{uuid}")
    public String editUser(@PathVariable UUID uuid, Model model) {

        return userService.findByUuid(uuid)
            .map(user -> {
                model.addAttribute("user", user);
                model.addAttribute("allRoles", roleService.findAll());
                return "userEdit";
            })
            .orElse(defaultRedirectPage);
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute User user) {
        userService.save(user);

        return defaultRedirectPage;
    }

    @DeleteMapping("/users/{uuid}")
    public String deleteUser(@PathVariable UUID uuid) {
        userService.deleteByUuid(uuid);

        return defaultRedirectPage;
    }

    @PutMapping("/users")
    public String updateUser(@ModelAttribute User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            // Если пароль не изменен, восстанавливаем старый пароль
            Optional<User> existingUser = userService.findByUuid(user.getUuid());
            existingUser.ifPresent(existing -> user.setPassword(existing.getPassword()));
        } else {
            // Если введен новый пароль, кодируем его
            user.setPassword(user.getPassword());
        }
        userService.save(user);

        return defaultRedirectPage;
    }

}
