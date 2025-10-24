package com.example.movie.report.dto;

import com.example.movie.domain.report.ReportStatus;

import jakarta.validation.constraints.NotNull;

public class ResolveReportRequest {

    @NotNull
    private Long adminId;

    @NotNull
    private ReportStatus status;

    private String notes;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
