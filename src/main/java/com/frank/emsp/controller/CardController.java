package com.frank.emsp.controller;

import com.frank.emsp.domain.Card;
import com.frank.emsp.service.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<Card> createCard(@Valid @RequestBody Card card) {
        return ResponseEntity.ok(cardService.createCard(card));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Card> changeStatus(@PathVariable Long id, @RequestBody String status) {
        Card.CardStatus cardStatus = Card.CardStatus.valueOf(status);
        return ResponseEntity.ok(cardService.changeStatus(id, cardStatus));
    }

    @GetMapping
    public ResponseEntity<Page<Card>> getCardsByLastUpdated(
            @RequestParam LocalDateTime lastUpdated,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(cardService.findByLastUpdated(lastUpdated, PageRequest.of(page, size)));
    }
}
