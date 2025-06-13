package com.frank.emsp.service;

import com.frank.emsp.domain.Card;
import com.frank.emsp.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Service
@Validated
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    public Card createCard(Card card) {
        if (cardRepository.existsByCardNumber(card.getCardNumber())) {
            throw new IllegalArgumentException("Card number already exists");
        }
        card.setStatus(Card.CardStatus.CREATED);
        return cardRepository.save(card);
    }

    public Card changeStatus(Long cardId, Card.CardStatus status) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));
        card.setStatus(status);
        return cardRepository.save(card);
    }

    public Page<Card> findByLastUpdated(LocalDateTime lastUpdated, Pageable pageable) {
        return cardRepository.findAllByLastUpdatedAfter(lastUpdated, pageable);
    }
}
