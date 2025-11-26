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

    //登录
    public UsersInfo selectByName(String username) {
        return usersInfoMapper.selectByName(username);
    }

    //注册
    public Integer insertData(String username, String password) {
        return usersInfoMapper.insertData(username,password);
    }

    //查找个人信息
    public UsersInfo selectById(Long userId) {
        return usersInfoMapper.selectById(userId);
    }

    public Boolean deleteUser(String username) {
        try {
            Integer result = usersInfoMapper.deleteUser(username);
            if (result==1){
                return true;
            }
        }catch (Exception e){
            log.error("退出登录失败, e:", e);
        }
        return false;
    }

    public Byte getVipStatus(String username) {
        return usersInfoMapper.selectVipStatusName(username);
    }

    public Boolean rechargeVip(String username) {
        // 1. 校验用户是否存在
        UsersInfo usersInfo = usersInfoMapper.selectUserByUsername(username);
        if (usersInfo == null) {
            return false; // 用户不存在
        }

        // 2. 校验是否已为VIP
        if (usersInfo.getVip() == 1) {
            return false; // 已为VIP，无需重复充值
        }

        // 3. 执行VIP状态更新
        int affectedRows = usersInfoMapper.updateVipStatus(username);
        return affectedRows > 0; // 返回更新结果
    }
}
