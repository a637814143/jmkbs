package com.example.movie.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.admin.dto.AdminDashboardView;
import com.example.movie.domain.ad.AdCampaignStatus;
import com.example.movie.domain.ad.AdCampaignRepository;
import com.example.movie.domain.ad.AdCreativeRepository;
import com.example.movie.domain.ad.AdCreativeStatus;
import com.example.movie.domain.movie.MovieRepository;
import com.example.movie.domain.report.ReportRepository;
import com.example.movie.domain.report.ReportStatus;
import com.example.movie.domain.review.ReviewRepository;
import com.example.movie.domain.review.ReviewStatus;
import com.example.movie.domain.user.UserRepository;

@Service
@Transactional(readOnly = true)
public class AdminService {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final ReportRepository reportRepository;
    private final AdCreativeRepository adCreativeRepository;
    private final AdCampaignRepository adCampaignRepository;
    private final UserRepository userRepository;

    public AdminService(MovieRepository movieRepository, ReviewRepository reviewRepository,
            ReportRepository reportRepository, AdCreativeRepository adCreativeRepository,
            AdCampaignRepository adCampaignRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.reportRepository = reportRepository;
        this.adCreativeRepository = adCreativeRepository;
        this.adCampaignRepository = adCampaignRepository;
        this.userRepository = userRepository;
    }

    public AdminDashboardView dashboard() {
        long totalMovies = movieRepository.count();
        long pendingReviews = reviewRepository.countByStatus(ReviewStatus.PENDING);
        long pendingReports = reportRepository.countByStatus(ReportStatus.PENDING);
        long pendingCreatives = adCreativeRepository.countByStatus(AdCreativeStatus.PENDING);
        long activeCampaigns = adCampaignRepository.countByStatus(AdCampaignStatus.ACTIVE);
        long totalUsers = userRepository.count();
        return new AdminDashboardView(totalMovies, pendingReviews, pendingReports, pendingCreatives, activeCampaigns,
                totalUsers);
    }
}
