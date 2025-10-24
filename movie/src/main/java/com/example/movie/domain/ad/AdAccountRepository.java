package com.example.movie.domain.ad;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdAccountRepository extends JpaRepository<AdAccount, Long> {

    Optional<AdAccount> findByUserId(Long userId);

    List<AdAccount> findByStatus(AdAccountStatus status);
}
