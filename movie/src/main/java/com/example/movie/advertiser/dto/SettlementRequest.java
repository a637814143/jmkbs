package com.example.movie.advertiser.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SettlementRequest {

    @NotNull
    @Min(0)
    private Long amountCents;

    public Long getAmountCents() {
        return amountCents;
    }

    public void setAmountCents(Long amountCents) {
        this.amountCents = amountCents;
    }
}
