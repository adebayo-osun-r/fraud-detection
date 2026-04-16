package com.zallpy.fraud.service;

import com.zallpy.fraud.domain.User;
import com.zallpy.fraud.dto.RegisterRequest;
import com.zallpy.fraud.enums.Role;
import com.zallpy.fraud.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User register(RegisterRequest request) {

    // check if user already exists
    userRepository.findByEmail(request.getEmail())
            .ifPresent(u -> {
                throw new RuntimeException("User already exists");
            });

    User user = User.builder()
            .fullName(request.getFullName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword())) // 🔐 encrypted
            .phone(request.getPhone())
            .country(request.getCountry())
            .state(request.getState())
            .isActive(true)
            .role(Role.USER)
            .build();

    return userRepository.save(user);
}
}