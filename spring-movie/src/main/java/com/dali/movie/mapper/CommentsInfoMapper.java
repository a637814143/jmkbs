package com.dali.movie.mapper;

import com.dali.movie.model.CommentsInfo;
import com.dali.movie.model.MoviesInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CommentsInfoMapper {
    /**
     * 查看自己的评论——id
     * @return
     */
    @Select("select * from comments where user_id = #{userId}")
    CommentsInfo getMyComment(Long userId);

    /**
     * 查看电影评论
     * @return
     */
    @Select("select * from comments where target_id = #{targetId}")
    CommentsInfo getMovieComment(Long targetId);

//    /**
//     * 发布评论
//     * @return
//     */
//    @Insert("insert into comments (content,user_id,target_id,parent_id,status,like_count,reply_count) values " +
//            "(#{content},#{userId},#{targetId},#{parentId}),#{status},#{likeCount},#{replyCount}")
//    Integer insert(CommentsInfo commentsInfo);

//    /**
//     * 插入评论，并返回自增ID
//     */
//    @Insert("INSERT INTO comments (content, user_id, target_id, parent_id, status, like_count, reply_count, created_at, updated_at) " +
//            "VALUES (#{content}, #{userId}, #{targetId}, #{parentId}, 0, 0, 0, NOW(), NOW())")
//    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
//    int insertComment(CommentsInfo commentsInfo);

    /**
     * 删除评论
     * @return
     */
    @Delete("delete from comments where id = #{id}")
    Integer deleteConment(Long id);
}
