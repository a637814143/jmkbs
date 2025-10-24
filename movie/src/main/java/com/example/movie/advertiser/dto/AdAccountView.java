package com.example.movie.advertiser.dto;

import com.example.movie.domain.ad.AdAccountStatus;

public class AdAccountView {

    private Long id;
    private Long userId;
    private String companyName;
    private AdAccountStatus status;
    private Long balanceCents;

    public AdAccountView() {
    }

    public AdAccountView(Long id, Long userId, String companyName, AdAccountStatus status, Long balanceCents) {
        this.id = id;
        this.userId = userId;
        this.companyName = companyName;
        this.status = status;
        this.balanceCents = balanceCents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public AdAccountStatus getStatus() {
        return status;
    }

    public void setStatus(AdAccountStatus status) {
        this.status = status;
    }

    public Long getBalanceCents() {
        return balanceCents;
    }

    public void setBalanceCents(Long balanceCents) {
        this.balanceCents = balanceCents;
    }
}
