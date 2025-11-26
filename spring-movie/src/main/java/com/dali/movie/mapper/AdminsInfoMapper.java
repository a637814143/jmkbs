package com.dali.movie.mapper;

import com.dali.movie.model.AdminsInfo;
import com.dali.movie.model.AdvertisersInfo;
import com.dali.movie.model.UsersInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminsInfoMapper {
    /**
     * 根据管理员名称信息进行登录
     * @return
     */
    @Select("select * from admins where status =1 and username = #{username}")
    AdminsInfo selectName(String username);

    /**
     * 查看管理员所负责的招商信息
     * @return
     */
    @Select("select * from advertisers where admin_Id = #{adminId}")
    AdvertisersInfo getAdminAdvertiser(Long adminId);
}
