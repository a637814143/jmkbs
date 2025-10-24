package com.example.movie.review.dto;

import jakarta.validation.constraints.NotNull;

public class ReviewModerationRequest {

    @NotNull
    private Long adminId;

    private String notes;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
