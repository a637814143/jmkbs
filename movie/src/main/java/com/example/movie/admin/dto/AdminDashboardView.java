package com.example.movie.admin.dto;

public class AdminDashboardView {

    private long totalMovies;
    private long pendingReviews;
    private long pendingReports;
    private long pendingCreatives;
    private long activeCampaigns;
    private long totalUsers;

    public AdminDashboardView() {
    }

    public AdminDashboardView(long totalMovies, long pendingReviews, long pendingReports, long pendingCreatives,
            long activeCampaigns, long totalUsers) {
        this.totalMovies = totalMovies;
        this.pendingReviews = pendingReviews;
        this.pendingReports = pendingReports;
        this.pendingCreatives = pendingCreatives;
        this.activeCampaigns = activeCampaigns;
        this.totalUsers = totalUsers;
    }

    public long getTotalMovies() {
        return totalMovies;
    }

    public void setTotalMovies(long totalMovies) {
        this.totalMovies = totalMovies;
    }

    public long getPendingReviews() {
        return pendingReviews;
    }

    public void setPendingReviews(long pendingReviews) {
        this.pendingReviews = pendingReviews;
    }

    public long getPendingReports() {
        return pendingReports;
    }

    public void setPendingReports(long pendingReports) {
        this.pendingReports = pendingReports;
    }

    public long getPendingCreatives() {
        return pendingCreatives;
    }

    public void setPendingCreatives(long pendingCreatives) {
        this.pendingCreatives = pendingCreatives;
    }

    public long getActiveCampaigns() {
        return activeCampaigns;
    }

    public void setActiveCampaigns(long activeCampaigns) {
        this.activeCampaigns = activeCampaigns;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }
}
