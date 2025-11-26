package com.dali.movie.model;

import lombok.Data;

import java.util.Date;

@Data
public class UsersInfo {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String nickname;
    private String avatar;
    private Byte status;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Byte vip;
}
