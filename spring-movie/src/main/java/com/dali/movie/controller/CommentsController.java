package com.dali.movie.controller;

import com.dali.movie.constant.Constants;
import com.dali.movie.model.CommentsInfo;
import com.dali.movie.model.MoviesInfo;
import com.dali.movie.service.CommentsService;
import com.dali.movie.service.MoviesService;
import com.dali.movie.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    /**
     * 查看自己的评论——id
     */
    @RequestMapping("/getMyComment")
    public CommentsInfo getMyComment(Long userId){
        log.info("getMyComment, userId: {}", userId);
        CommentsInfo commentsInfo = commentsService.getMyComment(userId);
        return commentsInfo;
    }

    /**
     * 查看电影评论
     */
    @RequestMapping("/getMovieComment")
    public CommentsInfo getMovieComment(Long targetId){
        log.info("getMovieComment, targetId: {}", targetId);
        CommentsInfo commentsInfo = commentsService.getMyComment(targetId);
        return commentsInfo;
    }

//    /**
//     * 发布评论
//     */
//    @RequestMapping("/comment/publish")
//    public String publishComment(CommentsInfo commentsInfo) {
//        log.info("发布评论请求，用户ID：{}，目标电影ID：{}", commentsInfo.getUserId(), commentsInfo.getTargetId());
//        Long commentId = commentsService.publishComment(commentsInfo);
//        if (commentId != null) {
//            return "评论发布成功，评论ID：" + commentId;
//        }
//        return "评论发布失败，请检查参数";
//    }

    /**
     * 删除评论
     */
    @RequestMapping("/deleteComment")
    public Boolean deleteComment(Long id) {
        log.info("删除评论,deleteComment(),id:{}",id);
        return commentsService.deleteComment(id);
    }
}
