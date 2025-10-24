package com.example.movie.advertiser.dto;

import java.time.LocalDateTime;

import com.example.movie.domain.ad.AdCampaignBidType;
import com.example.movie.domain.ad.AdCampaignPacing;
import com.example.movie.domain.ad.AdCampaignStatus;

public class AdCampaignView {

    private Long id;
    private Long accountId;
    private String name;
    private String objective;
    private Long budgetTotalCents;
    private Long budgetDailyCents;
    private AdCampaignBidType bidType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AdCampaignPacing pacing;
    private AdCampaignStatus status;

    public AdCampaignView() {
    }

    public AdCampaignView(Long id, Long accountId, String name, String objective, Long budgetTotalCents,
            Long budgetDailyCents, AdCampaignBidType bidType, LocalDateTime startTime, LocalDateTime endTime,
            AdCampaignPacing pacing, AdCampaignStatus status) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.objective = objective;
        this.budgetTotalCents = budgetTotalCents;
        this.budgetDailyCents = budgetDailyCents;
        this.bidType = bidType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pacing = pacing;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public Long getBudgetTotalCents() {
        return budgetTotalCents;
    }

    public void setBudgetTotalCents(Long budgetTotalCents) {
        this.budgetTotalCents = budgetTotalCents;
    }

    public Long getBudgetDailyCents() {
        return budgetDailyCents;
    }

    public void setBudgetDailyCents(Long budgetDailyCents) {
        this.budgetDailyCents = budgetDailyCents;
    }

    public AdCampaignBidType getBidType() {
        return bidType;
    }

    public void setBidType(AdCampaignBidType bidType) {
        this.bidType = bidType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public AdCampaignPacing getPacing() {
        return pacing;
    }

    public void setPacing(AdCampaignPacing pacing) {
        this.pacing = pacing;
    }

    public AdCampaignStatus getStatus() {
        return status;
    }

    public void setStatus(AdCampaignStatus status) {
        this.status = status;
    }
}
