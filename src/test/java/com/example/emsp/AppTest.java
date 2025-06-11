package com.example.emsp;

import com.example.emsp.domain.Account;
import com.example.emsp.domain.Card;
import com.example.emsp.repository.AccountRepository;
import com.example.emsp.repository.CardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@Transactional
public class AppTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;

    @Test
    void testApp() {
        Assertions.assertTrue(true);
    }

    @Test
    void testCreateAccount() {
        // /[a-z]{2}(-?)[\\da-z]{3}\\1[\\da-z]{9}(\\1[\\da-z])?/i
        // Account account = Account.builder().email("test@example.com").emaid("CN-123-987654321-z").status(Account.AccountStatus.CREATED).build();
        Account account = Account.builder().email("test@example.com").emaid("CN123987654321").status(Account.AccountStatus.CREATED).build();
        // Account account = Account.builder().email("test@example.com").emaid("ABC").status(Account.AccountStatus.CREATED).build();
        Account saved = accountRepository.save(account);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("test@example.com", saved.getEmail());
    }

    @Test
    void testCreateCard() {
        Card card = Card.builder().cardNumber("1234567890").rfid((long) 123456).status(Card.CardStatus.CREATED).build();
        Card saved = cardRepository.save(card);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("1234567890", saved.getCardNumber());
    }
}
