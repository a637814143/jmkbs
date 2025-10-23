package com.dali.movie.service;


import com.dali.movie.mapper.UsersInfoMapper;
import com.dali.movie.model.UsersInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsersService {
    @Autowired
    private UsersInfoMapper usersInfoMapper;

    public UsersInfo selectByName(String username) {
        return usersInfoMapper.selectByName(username);
    }

}
