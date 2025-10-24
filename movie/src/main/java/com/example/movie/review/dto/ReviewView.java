package com.example.movie.review.dto;

import java.time.Instant;

import com.example.movie.domain.review.ReviewStatus;

public class ReviewView {

    private Long id;
    private Long movieId;
    private Long userId;
    private String nickname;
    private Integer rating;
    private String title;
    private String content;
    private Boolean spoiler;
    private ReviewStatus status;
    private Integer likeCount;
    private Instant createdAt;

    public ReviewView() {
    }

    public ReviewView(Long id, Long movieId, Long userId, String nickname, Integer rating, String title,
            String content, Boolean spoiler, ReviewStatus status, Integer likeCount, Instant createdAt) {
        this.id = id;
        this.movieId = movieId;
        this.userId = userId;
        this.nickname = nickname;
        this.rating = rating;
        this.title = title;
        this.content = content;
        this.spoiler = spoiler;
        this.status = status;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getSpoiler() {
        return spoiler;
    }

    public void setSpoiler(Boolean spoiler) {
        this.spoiler = spoiler;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
