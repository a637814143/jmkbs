package com.example.movie.movie.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MovieDetailDto extends MovieSummaryDto {

    private List<String> directors;
    private List<String> casts;
    private String synopsis;
    private LocalDate releaseDate;

    public MovieDetailDto() {
    }

    public MovieDetailDto(Long id, String title, Integer releaseYear, String region, String language,
            List<String> genres, BigDecimal averageRating, Integer ratingsCount, String posterUrl,
            List<String> directors, List<String> casts, String synopsis, LocalDate releaseDate) {
        super(id, title, releaseYear, region, language, genres, averageRating, ratingsCount, posterUrl);
        this.directors = directors;
        this.casts = casts;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getCasts() {
        return casts;
    }

    public void setCasts(List<String> casts) {
        this.casts = casts;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
