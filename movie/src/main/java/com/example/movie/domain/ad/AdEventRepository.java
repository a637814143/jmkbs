package com.example.movie.domain.ad;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdEventRepository extends JpaRepository<AdEvent, Long> {

    long countByCreativeCampaignAccountId(Long accountId);

    long countByCreativeCampaignAccountIdAndEventType(Long accountId, AdEventType eventType);

    long countByEventType(AdEventType eventType);
}
