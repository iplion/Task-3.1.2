package ru.itmentor.spring.boot_security.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname", unique = true)
    private String nickname;

    public String getLogin() {
        return nickname != null && !nickname.trim().isEmpty()
            ? nickname
            : (name != null ? name : "unknown");
    }

    @Column(name = "password")
    private String password;

    @Column(name = "hair_color", nullable = false)
    private String hairColor;

    @Column(name = "must_die", nullable = false, updatable = false)
    private Boolean isMustDie = false;

    @Column(name = "updated_at", nullable = false, updatable = false)
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Role> roles;

    @PrePersist
    @PreUpdate
    private void validateNames() {
        if ((this.name == null || this.name.trim().isEmpty()) && (this.nickname == null || this.nickname.trim().isEmpty())) {
            throw new IllegalArgumentException("User must have either a nickname or a name at least once");
        }

        this.updatedAt = LocalDateTime.now();
    }

    public User(String name, String nickname, String hairColor, String password, List<Role> roles) {
        this.name = name;
        this.nickname = nickname;
        this.hairColor = hairColor;
        this.password = password;
        this.roles = roles;
    }
}
