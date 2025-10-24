package com.example.movie.user.dto;

import java.util.List;

import com.example.movie.report.dto.ReportView;
import com.example.movie.review.dto.ReviewView;

public class UserExportView {

    private UserProfileView profile;
    private List<ReviewView> reviews;
    private List<ReportView> reports;

    public UserExportView() {
    }

    public UserExportView(UserProfileView profile, List<ReviewView> reviews, List<ReportView> reports) {
        this.profile = profile;
        this.reviews = reviews;
        this.reports = reports;
    }

    public UserProfileView getProfile() {
        return profile;
    }

    public void setProfile(UserProfileView profile) {
        this.profile = profile;
    }

    public List<ReviewView> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewView> reviews) {
        this.reviews = reviews;
    }

    public List<ReportView> getReports() {
        return reports;
    }

    public void setReports(List<ReportView> reports) {
        this.reports = reports;
    }
}
