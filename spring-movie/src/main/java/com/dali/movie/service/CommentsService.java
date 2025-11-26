package com.dali.movie.service;

import com.dali.movie.mapper.CommentsInfoMapper;
import com.dali.movie.model.CommentsInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CommentsService {
    @Autowired
    private CommentsInfoMapper commentsInfoMapper;

    public CommentsInfo getMyComment(Long userId) {
        return commentsInfoMapper.getMyComment(userId);
    }

    public CommentsInfo getMovieComment(Long targetId) {
        return commentsInfoMapper.getMyComment(targetId);
    }

//    public Boolean insert(CommentsInfo commentsInfo) {
//        try{
//            Integer result = commentsInfoMapper.insert(commentsInfo);
//            if(result == 1) {
//                return true;
//            }
//        }catch(Exception e){
//            log.error("发布评论失败, e:",e);
//        }
//        return false;
//    }

    public Boolean deleteComment(Long id) {
        try {
            Integer result = commentsInfoMapper.deleteConment(id);
            if (result==1){
                return true;
            }
        }catch (Exception e){
            log.error("删除评论失败, e:", e);
        }
        return false;
    }

//    public Long publishComment(CommentsInfo commentsInfo) {
//        @Transactional
//        public Long publishComment(commentsInfo) {
//            // 参数校验：评论内容、用户ID、目标电影ID不能为空
//            if (comment.getContent() == null || comment.getUserId() == null || comment.getTargetId() == null) {
//                throw new IllegalArgumentException("评论内容、用户ID、目标电影ID不能为空");
//            }
//            // 初始化默认值：状态（待审核）、点赞数、回复数、时间
//            comment.setStatus(0);
//            comment.setLikeCount(0);
//            comment.setReplyCount(0);
//
//            // 执行插入
//            int result = commentMapper.insertComment(comment);
//            if (result > 0) {
//                return comment.getId(); // 返回评论ID
//            }
//            return null;
//        }
//    }
}
