package com.dali.movie.model;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationsInfo {
    private Long id;
    private String title;
    private String content;
    private Integer type;
    private Integer isRead;
    private Date createTime;
}
