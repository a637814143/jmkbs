package com.example.movie.movie;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.domain.movie.Movie;
import com.example.movie.domain.movie.MovieRepository;
import com.example.movie.movie.dto.MovieDetailDto;
import com.example.movie.movie.dto.MovieSearchResponse;
import com.example.movie.movie.dto.MovieSummaryDto;
import com.example.movie.movie.dto.MovieUpsertRequest;

@Service
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieSearchResponse search(String keyword, String region, String language, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Movie> result = movieRepository.search(emptyToNull(keyword), normalize(region), normalize(language), pageable);
        List<MovieSummaryDto> items = result.stream().map(this::toSummary).collect(Collectors.toList());
        return new MovieSearchResponse(items, result.getTotalElements(), page, size);
    }

    @Transactional(readOnly = true)
    public MovieDetailDto getDetail(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("电影不存在"));
        return toDetail(movie);
    }

    public MovieDetailDto create(MovieUpsertRequest request) {
        Movie movie = new Movie();
        apply(movie, request);
        if (request.getAverageRating() != null) {
            movie.setAverageRating(request.getAverageRating());
        }
        if (request.getRatingsCount() != null) {
            movie.setRatingsCount(request.getRatingsCount());
        }
        Movie saved = movieRepository.save(movie);
        return toDetail(saved);
    }

    public MovieDetailDto update(Long movieId, MovieUpsertRequest request) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("电影不存在"));
        apply(movie, request);
        if (request.getAverageRating() != null) {
            movie.setAverageRating(request.getAverageRating());
        }
        if (request.getRatingsCount() != null) {
            movie.setRatingsCount(request.getRatingsCount());
        }
        return toDetail(movie);
    }

    public void delete(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new IllegalArgumentException("电影不存在");
        }
        movieRepository.deleteById(movieId);
    }

    public void updateRating(Long movieId, BigDecimal average, int count) {
        movieRepository.findById(movieId).ifPresent(movie -> {
            movie.setAverageRating(average);
            movie.setRatingsCount(count);
        });
    }

    private void apply(Movie movie, MovieUpsertRequest request) {
        movie.setTitle(request.getTitle());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setRegion(request.getRegion());
        movie.setLanguage(request.getLanguage());
        movie.setGenres(request.getGenres());
        movie.setDirectors(request.getDirectors());
        movie.setCasts(request.getCasts());
        movie.setSynopsis(request.getSynopsis());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setReleaseDate(request.getReleaseDate());
    }

    private MovieSummaryDto toSummary(Movie movie) {
        return new MovieSummaryDto(movie.getId(), movie.getTitle(), movie.getReleaseYear(), movie.getRegion(),
                movie.getLanguage(), movie.getGenres(), movie.getAverageRating(), movie.getRatingsCount(),
                movie.getPosterUrl());
    }

    private MovieDetailDto toDetail(Movie movie) {
        return new MovieDetailDto(movie.getId(), movie.getTitle(), movie.getReleaseYear(), movie.getRegion(),
                movie.getLanguage(), movie.getGenres(), movie.getAverageRating(), movie.getRatingsCount(),
                movie.getPosterUrl(), movie.getDirectors(), movie.getCasts(), movie.getSynopsis(),
                movie.getReleaseDate());
    }

    private String normalize(String value) {
        return value == null || value.isBlank() ? null : value.toLowerCase();
    }

    private String emptyToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }
}
