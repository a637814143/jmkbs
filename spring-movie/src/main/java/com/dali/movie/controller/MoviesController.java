package com.dali.movie.controller;

import com.dali.movie.constant.Constants;
import com.dali.movie.model.MoviesInfo;
import com.dali.movie.model.Result;
import com.dali.movie.model.UsersInfo;
import com.dali.movie.service.MoviesService;
import com.dali.movie.utils.JwtUtils;
import com.dali.movie.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/movie")
public class MoviesController {
    @Autowired
    private MoviesService moviesService;

    /**
     * 通过电影名称搜索
     */
    @RequestMapping("/getMovieDetail")
    public MoviesInfo getBlogDetail(String title){
        log.info("getBlogDetail, title: {}", title);
        MoviesInfo moviesInfo = moviesService.getMovieDetail(title);
        return moviesInfo;
    }

    /**
     * 电影分类搜索电影列表
     */
    @RequestMapping("/getMovieGenre")
    public List<MoviesInfo> getMovieGenre(String genre){
        log.info("getMovieGenre, genre: {}", genre);
        List<MoviesInfo> moviesInfos = moviesService.getMovieGenre(genre);
        return moviesInfos;
    }

    /**
     * 电影发布时间搜索电影列表
     */
    @RequestMapping("/getMovieReleaseDate")
    public List<MoviesInfo> getMovieReleaseDate(String releaseDate){
        log.info("getMovieReleaseDate, releaseDate: {}", releaseDate);
        List<MoviesInfo> moviesInfos = moviesService.getMovieReleaseDate(releaseDate);
        return moviesInfos;
    }
}
