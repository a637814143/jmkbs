package com.example.movie.admin;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movie.admin.dto.AdminDashboardView;
import com.example.movie.advertiser.AdvertiserService;
import com.example.movie.advertiser.dto.AdAccountStatusRequest;
import com.example.movie.advertiser.dto.AdAccountView;
import com.example.movie.advertiser.dto.AdCreativeDecisionRequest;
import com.example.movie.advertiser.dto.AdCreativeView;
import com.example.movie.domain.ad.AdCreativeStatus;
import com.example.movie.movie.MovieService;
import com.example.movie.movie.dto.MovieDetailDto;
import com.example.movie.movie.dto.MovieUpsertRequest;
import com.example.movie.report.ReportService;
import com.example.movie.report.dto.ReportView;
import com.example.movie.report.dto.ResolveReportRequest;
import com.example.movie.review.ReviewService;
import com.example.movie.review.dto.ReviewModerationRequest;
import com.example.movie.review.dto.ReviewView;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@Validated
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class AdminController {

    private final AdminService adminService;
    private final MovieService movieService;
    private final ReviewService reviewService;
    private final ReportService reportService;
    private final AdvertiserService advertiserService;

    public AdminController(AdminService adminService, MovieService movieService, ReviewService reviewService,
            ReportService reportService, AdvertiserService advertiserService) {
        this.adminService = adminService;
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.reportService = reportService;
        this.advertiserService = advertiserService;
    }

    @GetMapping("/dashboard")
    public AdminDashboardView dashboard() {
        return adminService.dashboard();
    }

    @PostMapping("/movies")
    public MovieDetailDto createMovie(@Valid @RequestBody MovieUpsertRequest request) {
        return movieService.create(request);
    }

    @PostMapping("/movies/{movieId}")
    public MovieDetailDto updateMovie(@PathVariable Long movieId, @Valid @RequestBody MovieUpsertRequest request) {
        return movieService.update(movieId, request);
    }

    @PostMapping("/movies/{movieId}/delete")
    public void deleteMovie(@PathVariable Long movieId) {
        movieService.delete(movieId);
    }

    @GetMapping("/reviews/pending")
    public List<ReviewView> pendingReviews() {
        return reviewService.getPendingReviews();
    }

    @PostMapping("/reviews/{reviewId}/approve")
    public ReviewView approveReview(@PathVariable Long reviewId, @Valid @RequestBody ReviewModerationRequest request) {
        return reviewService.approve(reviewId, request);
    }

    @PostMapping("/reviews/{reviewId}/reject")
    public ReviewView rejectReview(@PathVariable Long reviewId, @Valid @RequestBody ReviewModerationRequest request) {
        return reviewService.reject(reviewId, request);
    }

    @GetMapping("/reports/pending")
    public List<ReportView> pendingReports() {
        return reportService.pending();
    }

    @PostMapping("/reports/{reportId}/resolve")
    public ReportView resolveReport(@PathVariable Long reportId, @Valid @RequestBody ResolveReportRequest request) {
        return reportService.resolve(reportId, request);
    }

    @GetMapping("/creatives/pending")
    public List<AdCreativeView> pendingCreatives() {
        return advertiserService.listCreativesByStatus(AdCreativeStatus.PENDING);
    }

    @PostMapping("/creatives/{creativeId}/decision")
    public AdCreativeView reviewCreative(@PathVariable Long creativeId,
            @Valid @RequestBody AdCreativeDecisionRequest request) {
        return advertiserService.reviewCreative(creativeId, request);
    }

    @PostMapping("/accounts/{accountId}/status")
    public AdAccountView updateAccountStatus(@PathVariable Long accountId,
            @Valid @RequestBody AdAccountStatusRequest request) {
        return advertiserService.updateAccountStatus(accountId, request);
    }

    @GetMapping("/accounts")
    public List<AdAccountView> accounts() {
        return advertiserService.listAccounts();
    }
}
