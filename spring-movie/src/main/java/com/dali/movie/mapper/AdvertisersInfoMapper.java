package com.dali.movie.mapper;

import com.dali.movie.model.AdvertisersInfo;
import com.dali.movie.model.CommentsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdvertisersInfoMapper {

    /**
     * 查看广告招商信息 —— id
     * @return
     */
    @Select("select * from advertisers where id = #{id}")
    AdvertisersInfo getIdAdvertiser(Long id);

    /**
     * 查看广告招商信息 —— 名称
     * @return
     */
    @Select("select * from advertisers where company_name = #{companyName}")
    AdvertisersInfo getNameAdvertiser(String companyName);

//    /**
//     * 查看广告招商是被管理员所管理的
//     * @return
//     */
//    @Select("select * from advertisers where admin_Id = #{adminId}")
//    AdvertisersInfo getNameAdvertiser(Long adminId);
}
