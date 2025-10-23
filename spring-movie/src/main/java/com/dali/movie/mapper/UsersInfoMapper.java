package com.dali.movie.mapper;

import com.dali.movie.model.UsersInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsersInfoMapper {
    /**
     * 根据用户名, 查询用户信息,用于密码验证等
     * @return
     */
    @Select("select * from users where status =1 and username = #{username}")
    UsersInfo selectByName(String username);
}
