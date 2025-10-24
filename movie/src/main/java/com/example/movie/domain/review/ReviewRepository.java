package com.example.movie.domain.review;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMovieIdAndStatusOrderByCreatedAtDesc(Long movieId, ReviewStatus status);

    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Review> findByMovieIdAndUserId(Long movieId, Long userId);

    List<Review> findByStatusOrderByCreatedAtAsc(ReviewStatus status);

    long countByStatus(ReviewStatus status);
}
