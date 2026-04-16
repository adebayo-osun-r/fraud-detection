package com.zallpy.fraud.controller;

import com.zallpy.fraud.domain.User;
import com.zallpy.fraud.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //  UNBLOCK USER
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/unblock/{userId}")
    public String unblockUser(@PathVariable Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setBlocked(false);
        user.setBlockReason(null);

        userRepository.save(user);

        return "User unblocked successfully";
    }
}