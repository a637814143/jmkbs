package com.example.movie.domain.ad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdCampaignRepository extends JpaRepository<AdCampaign, Long> {

    List<AdCampaign> findByAccountId(Long accountId);

    long countByStatus(AdCampaignStatus status);
}
