package com.example.movie.movie;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.movie.movie.dto.MovieDetailDto;
import com.example.movie.movie.dto.MovieSearchResponse;
import com.example.movie.movie.dto.MovieUpsertRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/movies")
@Validated
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public MovieSearchResponse search(@RequestParam(required = false) String keyword,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String language,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {
        return movieService.search(keyword, region, language, page, size);
    }

    @GetMapping("/{movieId}")
    public MovieDetailDto detail(@PathVariable Long movieId) {
        return movieService.getDetail(movieId);
    }

    @PostMapping
    public MovieDetailDto create(@Valid @RequestBody MovieUpsertRequest request) {
        return movieService.create(request);
    }

    @PutMapping("/{movieId}")
    public MovieDetailDto update(@PathVariable Long movieId, @Valid @RequestBody MovieUpsertRequest request) {
        return movieService.update(movieId, request);
    }

    @DeleteMapping("/{movieId}")
    public void delete(@PathVariable Long movieId) {
        movieService.delete(movieId);
    }
}
