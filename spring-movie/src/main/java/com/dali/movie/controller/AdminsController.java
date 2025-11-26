package com.dali.movie.controller;

import com.dali.movie.constant.Constants;
import com.dali.movie.model.AdminsInfo;
import com.dali.movie.model.AdvertisersInfo;
import com.dali.movie.model.Result;
import com.dali.movie.model.UsersInfo;
import com.dali.movie.service.AdminsService;
import com.dali.movie.service.UsersService;
import com.dali.movie.utils.JwtUtils;
import com.dali.movie.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminsController {
    @Autowired
    private AdminsService adminsService;
    /**
     * 登录
     */
    @RequestMapping("/login")
    public Result login(String username, String password) {
        log.info("UserController#login接收参数: username:{}, password:{}", username, password);
        //1. 参数校验合法性
        //2. 校验密码是否正确
        //3. 密码正确, 返回token
        //4. 密码错误, 返回错误信息
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
            return Result.fail("账号或密码不能为空");
        }
        //从数据库中查找用户
        AdminsInfo adminsInfo = adminsService.selectName(username);
        //1.用户不存在（没有拿到用户信息）
        if(adminsInfo == null) {
            log.error("管理员不存在");
            return Result.fail("管理员不存在");
        }
        //密码错误（防止空指针异常）数据（密码）不合法userInfo.getPassword() 这里多想一下（变一下位置）
        if (!SecurityUtils.verify(password, adminsInfo.getPassword())){
            log.error("密码错误");
            return Result.fail("密码错误");
        }
        //优先处理能处理完的——防止可读性变差（防止嵌套使用）
        Map<String,Object> claim = new HashMap<>();
        claim.put(Constants.TOKEN_ID,adminsInfo.getId());
        claim.put(Constants.TOKEN_USERNAME,adminsInfo.getUsername());
        //密码正确，返回token
        String token = JwtUtils.genJwtToken(claim);
        log.info("UserController#login 返回结果token:{}",token);
        return Result.success(token);
    }

    @RequestMapping("/advertiser")
    public AdvertisersInfo getAdminAdvertiser(Long adminId) {
        log.info("getAdminAdvertiser(),adminId: {}",adminId);
        AdvertisersInfo advertisersInfo = adminsService.getAdminAdvertiser(adminId);
        return advertisersInfo;
    }
}
