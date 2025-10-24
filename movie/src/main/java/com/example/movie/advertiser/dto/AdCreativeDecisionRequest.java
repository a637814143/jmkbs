package com.example.movie.advertiser.dto;

import com.example.movie.domain.ad.AdCreativeStatus;

import jakarta.validation.constraints.NotNull;

public class AdCreativeDecisionRequest {

    @NotNull
    private Long adminId;

    @NotNull
    private AdCreativeStatus status;

    private String auditNotes;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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
