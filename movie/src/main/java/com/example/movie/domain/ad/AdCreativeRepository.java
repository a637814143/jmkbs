package com.example.movie.domain.ad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdCreativeRepository extends JpaRepository<AdCreative, Long> {

    List<AdCreative> findByCampaignAccountId(Long accountId);

    List<AdCreative> findByCampaignId(Long campaignId);

    List<AdCreative> findByStatus(AdCreativeStatus status);

    long countByStatus(AdCreativeStatus status);
}
