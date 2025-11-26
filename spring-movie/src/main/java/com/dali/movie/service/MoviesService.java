package com.dali.movie.service;

import com.dali.movie.mapper.MoviesInfoMapper;
import com.dali.movie.mapper.UsersInfoMapper;
import com.dali.movie.model.MoviesInfo;
import com.dali.movie.model.UsersInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MoviesService {
    @Autowired
    private MoviesInfoMapper moviesInfoMapper;

    public MoviesInfo getMovieDetail(String username) {
        return moviesInfoMapper.getMovieDetail(username);
    }

    public List<MoviesInfo> getMovieGenre(String genre) {
        return moviesInfoMapper.getMovieGenre(genre);
    }

    public List<MoviesInfo> getMovieReleaseDate(String releaseDate) {
        // 1. 字符串转 Integer 年份（前端传 "2025" → 2025）
        Integer year = Integer.parseInt(releaseDate);
        return moviesInfoMapper.getMovieReleaseDate(year);
    }
}
