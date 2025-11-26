package com.dali.movie.controller;

import com.dali.movie.constant.Constants;
import com.dali.movie.model.Result;
import com.dali.movie.model.UsersInfo;
import com.dali.movie.service.UsersService;
import com.dali.movie.utils.JwtUtils;
import com.dali.movie.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UsersService usersService;

    /**
     * 注册
     */
    @RequestMapping("/register")
    public Result register(String username, String password) {
        log.info("UserController#register接收参数: username:{}, password:{}",
                username, password);

        // 1. 参数校验合法性（补充具体逻辑）
        if (username == null || username.trim().isEmpty()) {
            return Result.fail("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return Result.fail("密码不能为空");
        }

        //从数据库中查找用户
        UsersInfo userInfo = usersService.selectByName(username);
        if(userInfo != null) {
            log.error("当前用户已存在");
            return Result.fail("当前用户已存在");
        }

        // 2. 加密
        password = SecurityUtils.encrypt(password);

        // 3. 插入数据
        Integer number = usersService.insertData(username, password);
        if (number == null || number == 0) {
            log.error("注册失败，插入数据返回 null");
            return Result.fail("注册失败");
        }
        UsersInfo use= usersService.selectByName(username);

        Map<String,Object> claim = new HashMap<>();
        claim.put(Constants.TOKEN_ID,use.getId());
        claim.put(Constants.TOKEN_USERNAME,username);
        //注册成功，返回token
        String token = JwtUtils.genJwtToken(claim);
        log.info("UserController#register 返回结果token:{}",token);
        return Result.success(token);
    }

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
        UsersInfo userInfo = usersService.selectByName(username);
        //1.用户不存在（没有拿到用户信息）
        if(userInfo == null) {
            log.error("用户不存在");
            return Result.fail("用户不存在");
        }
        //密码错误（防止空指针异常）数据（密码）不合法userInfo.getPassword() 这里多想一下（变一下位置）
        if (!SecurityUtils.verify(password, userInfo.getPassword())){
            log.error("密码错误");
            return Result.fail("密码错误");
        }
        //优先处理能处理完的——防止可读性变差（防止嵌套使用）
        Map<String,Object> claim = new HashMap<>();
        claim.put(Constants.TOKEN_ID,userInfo.getId());
        claim.put(Constants.TOKEN_USERNAME,userInfo.getUsername());
        //密码正确，返回token
        String token = JwtUtils.genJwtToken(claim);
        log.info("UserController#login 返回结果token:{}",token);
        return Result.success(token);
    }

    /**
     * 获取当前登录用户的信息，主要用于个人信息展示
     * @return
     */
    @RequestMapping("/getUserInfo")
    public UsersInfo getLoginUserInfo(HttpServletRequest request){
        log.info("获取用户信息....");
        //获取token 请求头里面的信息是K:Y,这里的K为REQUEST_HEADER_TOKEN
        String token = request.getHeader(Constants.REQUEST_HEADER_TOKEN);
        //从token中获取登录用户ID
        Long userId = JwtUtils.getUserIdByToken(token);
        //数据返回的信息一般都会重新创建一个类来返回
        //UserInfo是服务端的实体类，通常情况下，UserInfo包含的信息并不符合客户端的需求
        //会对客户端需要的内容，新建一个类UserInfo2,把这两个类进行转换，
        UsersInfo userInfo = usersService.selectById(userId);
        //不需要返回密码——但是这样的设置不好看
        //userInfo.setPassword("");
        return userInfo;
    }

    //注销用户
    @RequestMapping("/deleteUser")
    public Boolean deleteUser(HttpServletRequest request){
        //获取登录用户
        //获取token
        String token = request.getHeader(Constants.REQUEST_HEADER_TOKEN);
        //从token中获取登录用户ID
        String username = JwtUtils.getUsernameByToken(token);
        if (username==null){
            return false;
        }
        log.info("注册账号,deleteUser(),username:{}",username);
        return usersService.deleteUser(username);
    }


    // 额外提供一个查询VIP状态的接口
    @RequestMapping ("/vipStatus")
    public Byte getVipStatus(String username) {
        log.info("getVipStatus(),username: {}",username);
        return usersService.getVipStatus(username);
    }

    //充值VIP
    @RequestMapping("/vipRecharge")
    public String rechargeVip(String username) {
        log.info("rechargeVip(),username: {}", username);
        Boolean result = usersService.rechargeVip(username);
        if (result) {
            return "VIP充值成功！";
        } else {
            return "VIP充值失败（用户不存在或已为VIP）";
        }
    }
}
