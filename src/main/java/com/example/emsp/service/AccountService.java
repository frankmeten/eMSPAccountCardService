package com.example.emsp.service;

import com.example.emsp.domain.Account;
import com.example.emsp.domain.Card;
import com.example.emsp.repository.AccountRepository;
import com.example.emsp.repository.CardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Validated
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;

    public Account createAccount(Account account) {
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        account.setStatus(Account.AccountStatus.CREATED);
        return accountRepository.save(account);
    }

    public Account changeStatus(Long accountId, Account.AccountStatus status) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setStatus(status);
        return accountRepository.save(account);
    }

    public Page<Account> findByLastUpdated(LocalDateTime lastUpdated, Pageable pageable) {
        return accountRepository.findAllByLastUpdatedAfter(lastUpdated, pageable);
    }

    @Transactional
    public Card assignCard(Long accountId, Long cardId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));
        card.setAccount(account);
        card.setStatus(Card.CardStatus.ASSIGNED);
        return cardRepository.save(card);
    }
}
