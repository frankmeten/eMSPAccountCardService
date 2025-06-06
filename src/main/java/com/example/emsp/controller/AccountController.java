package com.example.emsp.controller;

import com.example.emsp.domain.Account;
import com.example.emsp.domain.Card;
import com.example.emsp.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Account> changeStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Account.AccountStatus status = Account.AccountStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(accountService.changeStatus(id, status));
    }

    @PostMapping("/{accountId}/assign-card/{cardId}")
    public ResponseEntity<Card> assignCard(@PathVariable Long accountId, @PathVariable Long cardId) {
        return ResponseEntity.ok(accountService.assignCard(accountId, cardId));
    }

    @GetMapping
    public ResponseEntity<Page<Account>> getAccountsByLastUpdated(
            @RequestParam LocalDateTime lastUpdated,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(accountService.findByLastUpdated(lastUpdated, PageRequest.of(page, size)));
    }
}
