package com.example.movie.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.domain.user.User;
import com.example.movie.domain.user.UserRepository;
import com.example.movie.domain.user.UserStatus;
import com.example.movie.report.ReportService;
import com.example.movie.report.dto.ReportView;
import com.example.movie.review.ReviewService;
import com.example.movie.review.dto.ReviewView;
import com.example.movie.user.dto.UserExportView;
import com.example.movie.user.dto.UserProfileView;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ReviewService reviewService;
    private final ReportService reportService;

    public UserService(UserRepository userRepository, ReviewService reviewService, ReportService reportService) {
        this.userRepository = userRepository;
        this.reviewService = reviewService;
        this.reportService = reportService;
    }

    @Transactional(readOnly = true)
    public UserProfileView getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        return toProfile(user);
    }

    @Transactional(readOnly = true)
    public UserExportView export(Long userId) {
        UserProfileView profile = getProfile(userId);
        List<ReviewView> reviews = reviewService.getUserReviews(userId);
        List<ReportView> reports = reportService.findByReporter(userId);
        return new UserExportView(profile, reviews, reports);
    }

    public void deleteAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        user.setStatus(UserStatus.DELETED);
    }

    public List<UserProfileView> listAll() {
        return userRepository.findAll().stream().map(this::toProfile).toList();
    }

    private UserProfileView toProfile(User user) {
        return new UserProfileView(user.getId(), user.getUsername(), user.getNickname(), user.getAvatarUrl(),
                user.getRole(), user.getStatus(), user.getCreatedAt());
    }
}
