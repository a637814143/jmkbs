package com.example.movie.advertiser.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RechargeRequest {

    @NotNull
    @Min(1)
    private Long amountCents;

    public Long getAmountCents() {
        return amountCents;
    }

    public void setAmountCents(Long amountCents) {
        this.amountCents = amountCents;
    }
}
