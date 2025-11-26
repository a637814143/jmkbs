package com.dali.movie.service;

import com.dali.movie.mapper.NotificationsInfoMapper;
import com.dali.movie.model.NotificationsInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationsService {
    @Autowired
    private NotificationsInfoMapper notificationsInfoMapper;

    public Long publish(String title, String content, Integer type) {
        NotificationsInfo notification = new NotificationsInfo();
        notification.setTitle(title);
        notification.setContent(content);
        notification.setType(type);
        int result = notificationsInfoMapper.insertNotification(notification);
        if (result > 0) {
            return notification.getId();
        }
        return null;
    }

    public List<NotificationsInfo> list() {
        return notificationsInfoMapper.listNotifications();
    }
}
