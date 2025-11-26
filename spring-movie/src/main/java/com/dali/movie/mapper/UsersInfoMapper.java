package com.dali.movie.mapper;

import com.dali.movie.model.UsersInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UsersInfoMapper {
    /**
     * 根据用户名, 查询用户信息,用于密码验证等
     * @return
     */
    @Select("select * from users where status =1 and username = #{username}")
    UsersInfo selectByName(String username);

    /**
     * 用户注册时，插入数据
     */
    @Insert("insert into users (username,password) values (#{username},#{password})")
    Integer insertData(String username,String password);

    /**
     * 根据用户ID, 查询用户信息,用于个人中心的个人信息显示
     * @return
     */
    @Select("select * from users where status =1 and id = #{id}")
    UsersInfo selectById(Long id);

    /**
     * 根据用户ID, 注销
     * @return
     */
    @Delete("delete from users where username = #{username}")
    Integer deleteUser(String username);

    /**
     * 根据用户名, 查看vip状态
     * @return
     */
    @Select("select vip from users where username = #{username}")
    Byte selectVipStatusName(String username);

    // 查询用户（用于充值前校验）
    @Select("select username, vip from users where username = #{username}")
    UsersInfo selectUserByUsername(String username);

    /**
     * 充值VIP
     * @return
     */
    @Update("update users set vip = 1 where username = #{username}")
    int updateVipStatus(String username);


}
