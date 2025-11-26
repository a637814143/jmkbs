package com.dali.movie.model;

import lombok.Data;

import java.util.Date;

@Data
public class MoviesInfo {
    private Long id;
    private String title;
    private String originalTitle;
    private String director;
    private String screenwriter;
    private String actor;
    private String genre;
    private String country;
    private String language;
    private Date releaseDate;
    private Integer duration;
    private String description;
    private String posterUrl;
    private Float rating;
    private Integer ratingCount;
    private Byte status;
    private Long boxOffice;
    private Byte is3d;
    private Byte idImax;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
