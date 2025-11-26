package com.dali.movie.mapper;

import com.dali.movie.model.NotificationsInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationsInfoMapper {
    /**
     * 发布通知
     */
    @Insert("insert into notifications (title, content, type, is_read, create_time) " +
            "values (#{title}, #{content}, #{type}, 0, now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertNotification(NotificationsInfo notification);

    /**
     * 获取通知列表
     */
    @Select("select id, title, content, type, is_read as isRead, create_time as createTime " +
            "from notifications order by create_time desc")
    List<NotificationsInfo> listNotifications();
}
