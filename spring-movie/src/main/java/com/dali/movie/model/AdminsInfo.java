package com.dali.movie.model;

import lombok.Data;

import java.util.Date;

@Data
public class AdminsInfo {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private Byte status;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
