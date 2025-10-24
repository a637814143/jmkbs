package com.example.movie.advertiser.dto;

import com.example.movie.domain.ad.AdCreativeStatus;
import com.example.movie.domain.ad.AdCreativeType;

public class AdCreativeView {

    private Long id;
    private Long campaignId;
    private AdCreativeType type;
    private String assetUrl;
    private String clickUrl;
    private AdCreativeStatus status;
    private String auditNotes;

    public AdCreativeView() {
    }

    public AdCreativeView(Long id, Long campaignId, AdCreativeType type, String assetUrl, String clickUrl,
            AdCreativeStatus status, String auditNotes) {
        this.id = id;
        this.campaignId = campaignId;
        this.type = type;
        this.assetUrl = assetUrl;
        this.clickUrl = clickUrl;
        this.status = status;
        this.auditNotes = auditNotes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public AdCreativeType getType() {
        return type;
    }

    public void setType(AdCreativeType type) {
        this.type = type;
    }

    public String getAssetUrl() {
        return assetUrl;
    }

    public void setAssetUrl(String assetUrl) {
        this.assetUrl = assetUrl;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public AdCreativeStatus getStatus() {
        return status;
    }

    public void setStatus(AdCreativeStatus status) {
        this.status = status;
    }

    public String getAuditNotes() {
        return auditNotes;
    }

    public void setAuditNotes(String auditNotes) {
        this.auditNotes = auditNotes;
    }
}
