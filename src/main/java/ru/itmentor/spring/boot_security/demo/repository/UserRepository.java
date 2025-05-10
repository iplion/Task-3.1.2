package ru.itmentor.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUuid(UUID uuid);

    @Query("SELECT u FROM User u WHERE u.nickname = :login OR u.name = :login")
    Optional<User> findByNicknameOrName(@Param("login") String login);

    @EntityGraph(attributePaths = {"roles"})
    @Query("SELECT u FROM User u WHERE u.nickname = :login OR u.name = :login")
    Optional<User> findByNicknameOrNameAddRole(@Param("login") String login);

    void deleteByUuid(UUID uuid);
}
