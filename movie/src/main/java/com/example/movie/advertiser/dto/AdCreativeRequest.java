package com.example.movie.advertiser.dto;

import com.example.movie.domain.ad.AdCreativeType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AdCreativeRequest {

    @NotNull
    private Long campaignId;

    @NotNull
    private AdCreativeType type;

    @NotBlank
    private String assetUrl;

    private String clickUrl;

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
}
