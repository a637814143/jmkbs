package com.example.movie.domain.reviewlike;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, ReviewLikeId> {

    boolean existsByIdReviewIdAndIdUserId(Long reviewId, Long userId);

    long countByIdReviewId(Long reviewId);

    void deleteByIdReviewIdAndIdUserId(Long reviewId, Long userId);
}
