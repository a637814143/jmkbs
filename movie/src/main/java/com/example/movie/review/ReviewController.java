package com.example.movie.review;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.movie.domain.review.ReviewStatus;
import com.example.movie.review.dto.ReviewCreateRequest;
import com.example.movie.review.dto.ReviewLikeRequest;
import com.example.movie.review.dto.ReviewView;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Validated
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/movies/{movieId}/reviews")
    public List<ReviewView> listMovieReviews(@PathVariable Long movieId,
            @RequestParam(required = false) ReviewStatus status) {
        return reviewService.getMovieReviews(movieId, status);
    }

    @PostMapping("/movies/{movieId}/reviews")
    public ReviewView createReview(@PathVariable Long movieId, @Valid @RequestBody ReviewCreateRequest request) {
        return reviewService.createReview(movieId, request);
    }

    @GetMapping("/users/{userId}/reviews")
    public List<ReviewView> listUserReviews(@PathVariable Long userId) {
        return reviewService.getUserReviews(userId);
    }

    @PostMapping("/reviews/{reviewId}/like")
    public void likeReview(@PathVariable Long reviewId, @Valid @RequestBody ReviewLikeRequest request) {
        reviewService.likeReview(reviewId, request.getUserId());
    }

    @DeleteMapping("/reviews/{reviewId}/like")
    public void unlikeReview(@PathVariable Long reviewId, @Valid @RequestBody ReviewLikeRequest request) {
        reviewService.unlikeReview(reviewId, request.getUserId());
    }

}
