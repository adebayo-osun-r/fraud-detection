package com.zallpy.fraud.controller;

import com.zallpy.fraud.domain.LoginEvent;
import com.zallpy.fraud.service.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/{userId}")
    public LoginEvent login(
            @PathVariable Long userId,
            @RequestBody LoginEvent event) {

        return loginService.processLogin(userId, event);
    }
}