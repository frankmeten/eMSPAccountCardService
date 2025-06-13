package com.frank.emsp.repository;

import com.frank.emsp.domain.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByCardNumber(String cardNumber);
    Page<Card> findAllByLastUpdatedAfter(LocalDateTime lastUpdated, Pageable pageable);
}
