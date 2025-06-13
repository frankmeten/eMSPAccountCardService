package com.example.emsp;

import com.example.emsp.domain.Account;
import com.example.emsp.domain.Card;
import com.example.emsp.repository.AccountRepository;
import com.example.emsp.repository.CardRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

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

    @org.junit.jupiter.api.BeforeEach
    @org.junit.jupiter.api.AfterEach
    void cleanDatabase() {
        cardRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void testApp() {
        Assertions.assertTrue(true);
    }

    @Test
    void testCreateAccount() {
        Account account = Account.builder()
            .email("test@example.com")
            .emaid("us123987654321")
            .status(Account.AccountStatus.CREATED)
            .build();
        Account saved = accountRepository.save(account);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("test@example.com", saved.getEmail());
    }

    @Test
    void testChangeAccountStatus() {
        Account account = Account.builder()
                .email("status@example.com")
                .emaid("DE123987654321")
                .status(Account.AccountStatus.CREATED)
                .build();
        Account saved = accountRepository.save(account);
        saved.setStatus(Account.AccountStatus.ACTIVATED);
        Account updated = accountRepository.save(saved);
        Assertions.assertEquals(Account.AccountStatus.ACTIVATED, updated.getStatus());
    }

    @Test
    void testCreateCard() {
        Card card = Card.builder()
                .cardNumber("1234567890")
                .rfid(123456L)
                .status(Card.CardStatus.CREATED)
                .build();
        Card saved = cardRepository.save(card);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("1234567890", saved.getCardNumber());
    }

    @Test
    void testAssignCardToAccount() {
        Account account = Account.builder()
                .email("assign@example.com")
                .emaid("FR123987654321")
                .status(Account.AccountStatus.CREATED)
                .build();
        Account savedAccount = accountRepository.save(account);

        Card card = Card.builder()
                .cardNumber("9876543210")
                .rfid(654321L)
                .status(Card.CardStatus.CREATED)
                .account(savedAccount)
                .build();
        Card savedCard = cardRepository.save(card);

        Assertions.assertNotNull(savedCard.getAccount());
        Assertions.assertEquals(savedAccount.getId(), savedCard.getAccount().getId());
    }

    @Test
    void testChangeCardStatus() {
        Card card = Card.builder()
                .cardNumber("5555555555")
                .rfid(555555L)
                .status(Card.CardStatus.CREATED)
                .build();
        Card saved = cardRepository.save(card);
        saved.setStatus(Card.CardStatus.ACTIVATED);
        Card updated = cardRepository.save(saved);
        Assertions.assertEquals(Card.CardStatus.ACTIVATED, updated.getStatus());
    }

    @Test
    void testQueryAccountsByLastUpdatedWithPagination() {
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 5; i++) {
            Account account = Account.builder()
                    .email("user" + i + "@example.com")
                    .emaid("IT12398765432" + i)
                    .status(Account.AccountStatus.CREATED)
                    .build();
            account.setLastUpdated(now.minusDays(i));
            accountRepository.save(account);
        }
        Page<Account> page = accountRepository.findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "lastUpdated")));
        Assertions.assertEquals(2, page.getContent().size());
        Assertions.assertTrue(page.getContent().get(0).getLastUpdated().isAfter(page.getContent().get(1).getLastUpdated())
                || page.getContent().get(0).getLastUpdated().isEqual(page.getContent().get(1).getLastUpdated()));
    }

    @Test
    void testQueryCardsByLastUpdatedWithPagination() {
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 5; i++) {
            Card card = Card.builder()
                    .cardNumber("CARD" + i)
                    .rfid(100000L + i)
                    .status(Card.CardStatus.CREATED)
                    .lastUpdated(now.minusHours(i))
                    .build();
            cardRepository.save(card);
        }
        Page<Card> page = cardRepository.findAll(PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "lastUpdated")));
        Assertions.assertEquals(3, page.getContent().size());
        Assertions.assertTrue(page.getContent().get(0).getLastUpdated().isAfter(page.getContent().get(1).getLastUpdated())
                || page.getContent().get(0).getLastUpdated().isEqual(page.getContent().get(1).getLastUpdated()));
    }


    @Test
    void testCreateAccountAndAssignCardIntegration() {
        // 创建账户
        Account account = Account.builder()
                .email("integration@example.com")
                .emaid("IN123987654321")
                .status(Account.AccountStatus.CREATED)
                .build();
        Account savedAccount = accountRepository.save(account);

        // 创建卡并分配给账户
        Card card = Card.builder()
                .cardNumber("INTEGRATION123")
                .rfid(999999L)
                .status(Card.CardStatus.CREATED)
                .account(savedAccount)
                .build();
        Card savedCard = cardRepository.save(card);

        // 校验
        Assertions.assertNotNull(savedAccount.getId());
        Assertions.assertNotNull(savedCard.getId());
        Assertions.assertEquals(savedAccount.getId(), savedCard.getAccount().getId());

        // 修改账户和卡状态
        savedAccount.setStatus(Account.AccountStatus.ACTIVATED);
        accountRepository.save(savedAccount);
        savedCard.setStatus(Card.CardStatus.ACTIVATED);
        cardRepository.save(savedCard);

        // 查询并校验
        Account foundAccount = accountRepository.findById(savedAccount.getId()).orElseThrow();
        Card foundCard = cardRepository.findById(savedCard.getId()).orElseThrow();
        Assertions.assertEquals(Account.AccountStatus.ACTIVATED, foundAccount.getStatus());
        Assertions.assertEquals(Card.CardStatus.ACTIVATED, foundCard.getStatus());
    }
    
}
