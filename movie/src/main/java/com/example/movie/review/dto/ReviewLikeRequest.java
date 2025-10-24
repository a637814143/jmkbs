package com.example.movie.review.dto;

import jakarta.validation.constraints.NotNull;

public class ReviewLikeRequest {

    @NotNull
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
