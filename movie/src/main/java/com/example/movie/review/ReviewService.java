package com.example.movie.review;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.domain.movie.Movie;
import com.example.movie.domain.movie.MovieRepository;
import com.example.movie.domain.review.Review;
import com.example.movie.domain.review.ReviewRepository;
import com.example.movie.domain.review.ReviewStatus;
import com.example.movie.domain.reviewlike.ReviewLike;
import com.example.movie.domain.reviewlike.ReviewLikeId;
import com.example.movie.domain.reviewlike.ReviewLikeRepository;
import com.example.movie.domain.user.User;
import com.example.movie.domain.user.UserRepository;
import com.example.movie.movie.MovieService;
import com.example.movie.review.dto.ReviewCreateRequest;
import com.example.movie.review.dto.ReviewModerationRequest;
import com.example.movie.review.dto.ReviewView;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final MovieService movieService;

    public ReviewService(ReviewRepository reviewRepository, MovieRepository movieRepository,
            UserRepository userRepository, ReviewLikeRepository reviewLikeRepository, MovieService movieService) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.reviewLikeRepository = reviewLikeRepository;
        this.movieService = movieService;
    }

    public ReviewView createReview(Long movieId, ReviewCreateRequest request) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("电影不存在"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        reviewRepository.findByMovieIdAndUserId(movieId, user.getId()).ifPresent(existing -> {
            throw new IllegalStateException("您已提交过该影片的影评");
        });
        Review review = new Review();
        review.setMovie(movie);
        review.setUser(user);
        review.setRating(request.getRating());
        review.setTitle(request.getTitle());
        review.setContent(request.getContent());
        review.setSpoiler(Boolean.TRUE.equals(request.getSpoiler()));
        review.setStatus(ReviewStatus.PENDING);
        review.setLikeCount(0);
        Review saved = reviewRepository.save(review);
        return toView(saved);
    }

    @Transactional(readOnly = true)
    public List<ReviewView> getMovieReviews(Long movieId, ReviewStatus status) {
        ReviewStatus effectiveStatus = status != null ? status : ReviewStatus.APPROVED;
        return reviewRepository.findByMovieIdAndStatusOrderByCreatedAtDesc(movieId, effectiveStatus)
                .stream()
                .map(this::toView)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewView> getUserReviews(Long userId) {
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(this::toView)
                .collect(Collectors.toList());
    }

    public void likeReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("影评不存在"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        ReviewLikeId id = new ReviewLikeId(review.getId(), user.getId());
        if (reviewLikeRepository.existsById(id)) {
            throw new IllegalStateException("已经点赞过该影评");
        }
        ReviewLike like = new ReviewLike();
        like.setId(id);
        like.setReview(review);
        like.setUser(user);
        like.setCreatedAt(Instant.now());
        reviewLikeRepository.save(like);
        updateLikeCount(review);
    }

    public void unlikeReview(Long reviewId, Long userId) {
        ReviewLikeId id = new ReviewLikeId(reviewId, userId);
        if (reviewLikeRepository.existsById(id)) {
            reviewLikeRepository.deleteById(id);
            reviewRepository.findById(reviewId).ifPresent(this::updateLikeCount);
        }
    }

    @Transactional(readOnly = true)
    public List<ReviewView> getPendingReviews() {
        return reviewRepository.findByStatusOrderByCreatedAtAsc(ReviewStatus.PENDING).stream().map(this::toView)
                .collect(Collectors.toList());
    }

    public ReviewView approve(Long reviewId, ReviewModerationRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("影评不存在"));
        review.setStatus(ReviewStatus.APPROVED);
        refreshMovieRating(review.getMovie().getId());
        return toView(review);
    }

    public ReviewView reject(Long reviewId, ReviewModerationRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("影评不存在"));
        review.setStatus(ReviewStatus.REJECTED);
        refreshMovieRating(review.getMovie().getId());
        return toView(review);
    }

    private void refreshMovieRating(Long movieId) {
        List<Review> approved = reviewRepository.findByMovieIdAndStatusOrderByCreatedAtDesc(movieId,
                ReviewStatus.APPROVED);
        if (approved.isEmpty()) {
            movieService.updateRating(movieId, BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), 0);
            return;
        }
        int count = approved.size();
        BigDecimal total = approved.stream()
                .map(review -> BigDecimal.valueOf(review.getRating()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal average = total.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        movieService.updateRating(movieId, average, count);
    }

    private void updateLikeCount(Review review) {
        long count = reviewLikeRepository.countByIdReviewId(review.getId());
        review.setLikeCount(Math.toIntExact(count));
    }

    private ReviewView toView(Review review) {
        return new ReviewView(review.getId(), review.getMovie().getId(), review.getUser().getId(),
                review.getUser().getNickname(), review.getRating(), review.getTitle(), review.getContent(),
                review.getSpoiler(), review.getStatus(), review.getLikeCount(), review.getCreatedAt());
    }
}
