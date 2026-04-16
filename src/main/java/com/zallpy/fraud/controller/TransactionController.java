package com.zallpy.fraud.controller;

import com.zallpy.fraud.domain.Transaction;
import com.zallpy.fraud.service.TransactionService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public Transaction processTransaction(
            @PathVariable Long userId,
            @RequestBody Transaction transaction) {

        return transactionService.processTransaction(userId, transaction);
    }
}