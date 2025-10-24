package com.example.movie.domain.ad;

import java.time.LocalDateTime;

import com.example.movie.domain.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ad_campaign")
public class AdCampaign extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private AdAccount account;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 100)
    private String objective;

    @Column(name = "budget_total_cents")
    private Long budgetTotalCents = 0L;

    @Column(name = "budget_daily_cents")
    private Long budgetDailyCents = 0L;

    @Enumerated(EnumType.STRING)
    @Column(name = "bid_type", nullable = false, length = 10)
    private AdCampaignBidType bidType = AdCampaignBidType.CPM;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private AdCampaignPacing pacing = AdCampaignPacing.EVEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private AdCampaignStatus status = AdCampaignStatus.DRAFT;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdAccount getAccount() {
        return account;
    }

    public void setAccount(AdAccount account) {
        this.account = account;
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
