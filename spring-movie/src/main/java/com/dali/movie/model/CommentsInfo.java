package com.dali.movie.model;

import lombok.Data;

import java.util.Date;

@Data
public class CommentsInfo {
    private Long id;
    private String content;
    private Long userId;
    private Long targetId;
    private Long parentId;
    private Byte status;
    private Integer likeCount;
    private Integer replyCount;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
