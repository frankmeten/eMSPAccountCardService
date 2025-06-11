package com.example.emsp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    // Lombok 不要自动生成 setEmaid
    @Setter(AccessLevel.NONE)
    @NotBlank
    @Pattern(
        regexp = "[a-z]{2}(-?)[\\da-z]{3}\\1[\\da-z]{9}(\\1[\\da-z])?",
        flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "Invalid EMAID format"
    )
    @Column(unique = true, nullable = false, length = 14)
    private String emaid;


    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    public void setEmaid(String emaid) {
        if (emaid != null) {
            String cleaned = emaid.replace("-", "");
            if (cleaned.length() == 15) {
                cleaned = cleaned.substring(0, 14);
            }
            this.emaid = cleaned;
        } else {
            this.emaid = null;
        }
    }

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdated = LocalDateTime.now();
    }

    public enum AccountStatus {
        CREATED, ACTIVATED, DEACTIVATED
    }
}
