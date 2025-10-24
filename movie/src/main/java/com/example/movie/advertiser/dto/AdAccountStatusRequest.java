package com.example.movie.advertiser.dto;

import com.example.movie.domain.ad.AdAccountStatus;

import jakarta.validation.constraints.NotNull;

public class AdAccountStatusRequest {

    @NotNull
    private Long adminId;

    @NotNull
    private AdAccountStatus status;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public AdAccountStatus getStatus() {
        return status;
    }

    public void setStatus(AdAccountStatus status) {
        this.status = status;
    }
}
