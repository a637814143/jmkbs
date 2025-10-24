package com.example.movie.domain.ad;

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
@Table(name = "ad_creative")
public class AdCreative extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campaign_id", nullable = false)
    private AdCampaign campaign;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private AdCreativeType type = AdCreativeType.IMG;

    @Column(name = "asset_url", nullable = false, length = 255)
    private String assetUrl;

    @Column(name = "click_url", length = 255)
    private String clickUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AdCreativeStatus status = AdCreativeStatus.PENDING;

    @Column(name = "audit_notes", columnDefinition = "TEXT")
    private String auditNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(AdCampaign campaign) {
        this.campaign = campaign;
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
