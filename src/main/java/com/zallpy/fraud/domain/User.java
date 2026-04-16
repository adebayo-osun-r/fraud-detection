package com.zallpy.fraud.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import com.zallpy.fraud.enums.Role;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    private String country;

    private String state;

    private boolean isActive;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private boolean isBlocked;

    private String blockReason;

    @CreationTimestamp
    private LocalDateTime createdAt;
}