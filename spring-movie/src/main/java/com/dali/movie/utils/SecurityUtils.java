package com.dali.movie.utils;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class SecurityUtils {
    /**
     * 加密
     * password 用户注册时输入的密码
     * return: 数据库中存储的信息:  盐值 + md5(明文+盐值)
     */
    public static String encrypt(String password){
        //生成随机盐值
        String salt = UUID.randomUUID().toString().replace("-","");
        //对 明文+盐值  进行MD5加密 =>   md5(明文+盐值)
        String finalPassword = DigestUtils.md5DigestAsHex((password+salt).getBytes());
        return salt+finalPassword;
    }

    /**
     * 验证密码是否正确
     * @param inputPassword  用户登录时输入的密码
     * @param sqlPassword  数据库中password字段存储的信息   盐值 + md5(明文+盐值)
     * @return
     */
    public static boolean verify(String inputPassword, String sqlPassword){
        if (!StringUtils.hasLength(inputPassword)){
            return false;
        }
        if (sqlPassword==null || sqlPassword.length() !=64){
            return false;
        }
        //获取盐值
        String salt = sqlPassword.substring(0,32);
        //根据用户登录输入的密码和盐值, 进行加密    md5(明文+盐值)
        String finalPassword = DigestUtils.md5DigestAsHex((inputPassword+salt).getBytes());

        return (salt+finalPassword).equals(sqlPassword);
    }

//    public static void main(String[] args) {
//        System.out.println(encrypt("123456"));
//    }
}
