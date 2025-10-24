package com.example.movie.advertiser.dto;

import java.util.Map;

public class AdDashboardView {

    private Long accountId;
    private Long balanceCents;
    private long impressionCount;
    private long clickCount;
    private Map<String, Long> campaignStatus;
    private Map<String, Long> creativeStatus;

    public AdDashboardView() {
    }

    public AdDashboardView(Long accountId, Long balanceCents, long impressionCount, long clickCount,
            Map<String, Long> campaignStatus, Map<String, Long> creativeStatus) {
        this.accountId = accountId;
        this.balanceCents = balanceCents;
        this.impressionCount = impressionCount;
        this.clickCount = clickCount;
        this.campaignStatus = campaignStatus;
        this.creativeStatus = creativeStatus;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getBalanceCents() {
        return balanceCents;
    }

    public void setBalanceCents(Long balanceCents) {
        this.balanceCents = balanceCents;
    }

    public long getImpressionCount() {
        return impressionCount;
    }

    public void setImpressionCount(long impressionCount) {
        this.impressionCount = impressionCount;
    }

    public long getClickCount() {
        return clickCount;
    }

    public void setClickCount(long clickCount) {
        this.clickCount = clickCount;
    }

    public Map<String, Long> getCampaignStatus() {
        return campaignStatus;
    }

    public void setCampaignStatus(Map<String, Long> campaignStatus) {
        this.campaignStatus = campaignStatus;
    }

    public Map<String, Long> getCreativeStatus() {
        return creativeStatus;
    }

    public void setCreativeStatus(Map<String, Long> creativeStatus) {
        this.creativeStatus = creativeStatus;
    }
}
