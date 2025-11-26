package com.dali.movie.model;

import lombok.Data;

import java.util.Date;

@Data
public class AdvertisersInfo {
    private Long id;
    private String companyName;
    private String contactPerson;
    private String contactPhone;
    private Long userId;
    private Byte industryType;
    private Byte status;
    private Float balance;
    private Date startTime;
    private Date endTime;
    private Long adminId;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
