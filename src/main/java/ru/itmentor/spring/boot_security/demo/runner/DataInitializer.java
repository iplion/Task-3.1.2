package ru.itmentor.spring.boot_security.demo.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.entity.Role;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        /// Initializing Data...");
        if (userService.findAll().isEmpty()) {
            Role roleUser = roleService.save("ROLE_USER");
            Role roleAdmin = roleService.save("ROLE_ADMIN");
            userService.save(new User("Gogi", null, "black", userService.encodePassword("password"), Arrays.asList(roleUser)));
            userService.save(new User("Vasilek", "user", "red", userService.encodePassword("password"), Arrays.asList(roleUser)));
            userService.save(new User("", "null", "blue", userService.encodePassword("password"), Arrays.asList(roleUser)));
            userService.save(new User(null, "admin", "yellow", userService.encodePassword("password"), Arrays.asList(roleAdmin)));
            userService.save(new User("Gavrilla", null, "purple", userService.encodePassword("password"), Arrays.asList(roleUser, roleAdmin)));
        }
    }
}
