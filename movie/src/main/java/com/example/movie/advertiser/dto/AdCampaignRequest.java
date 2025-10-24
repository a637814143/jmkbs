package com.example.movie.advertiser.dto;

import java.time.LocalDateTime;

import com.example.movie.domain.ad.AdCampaignBidType;
import com.example.movie.domain.ad.AdCampaignPacing;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AdCampaignRequest {

    @NotNull
    private Long accountId;

    @NotBlank
    private String name;

    private String objective;

    @NotNull
    private Long budgetTotalCents;

    @NotNull
    private Long budgetDailyCents;

    @NotNull
    private AdCampaignBidType bidType;

    @NotNull
    private LocalDateTime startTime;

    @Future
    private LocalDateTime endTime;

    @NotNull
    private AdCampaignPacing pacing;

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
}
