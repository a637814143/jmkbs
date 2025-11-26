package com.dali.movie.service;

import com.dali.movie.mapper.AdminsInfoMapper;
import com.dali.movie.mapper.UsersInfoMapper;
import com.dali.movie.model.AdminsInfo;
import com.dali.movie.model.AdvertisersInfo;
import com.dali.movie.model.UsersInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminsService {
    @Autowired
    private AdminsInfoMapper adminsInfoMapper;

    //管理员登录
    public AdminsInfo selectName(String username) {
        return adminsInfoMapper.selectName(username);
    }

    public AdvertisersInfo getAdminAdvertiser(Long adminId) {
        return adminsInfoMapper.getAdminAdvertiser(adminId);
    }
}
