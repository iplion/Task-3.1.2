package ru.itmentor.spring.boot_security.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUuid(UUID uuid) {
        return  userRepository.findByUuid(uuid);
    }

    public Optional<User> findByLogin(String login) {
        return  userRepository.findByNicknameOrNameAddRole(login);
    }

//    public String getLogin(User user) {
//        return (user.getName() == null || user.getName().trim().isEmpty()) ? user.getNickname() : user.getName();
//    }

    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Transactional
    public void deleteByUuid(UUID uuid) {
        userRepository.deleteByUuid(uuid);
    }
}
