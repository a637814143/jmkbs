package com.example.movie.movie.dto;

import java.math.BigDecimal;
import java.util.List;

public class MovieSummaryDto {

    private Long id;
    private String title;
    private Integer releaseYear;
    private String region;
    private String language;
    private List<String> genres;
    private BigDecimal averageRating;
    private Integer ratingsCount;
    private String posterUrl;

    public MovieSummaryDto() {
    }

    public MovieSummaryDto(Long id, String title, Integer releaseYear, String region, String language,
            List<String> genres, BigDecimal averageRating, Integer ratingsCount, String posterUrl) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.region = region;
        this.language = language;
        this.genres = genres;
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
        this.posterUrl = posterUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
