package com.example.emsp.repository;

import com.example.emsp.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmaid(String emaid);
    Page<Account> findAllByLastUpdatedAfter(LocalDateTime lastUpdated, Pageable pageable);
}
