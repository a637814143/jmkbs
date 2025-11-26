package com.dali.movie.controller;

import com.dali.movie.model.NotificationsInfo;
import com.dali.movie.model.Result;
import com.dali.movie.service.NotificationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationsController {
    @Autowired
    private NotificationsService notificationsService;

    /**
     * 发布通知
     */
    @RequestMapping("/publish")
    public Result<Long> publish(String title, String content, Integer type) {
        log.info("publish notification, title:{}, type:{}", title, type);
        if (!StringUtils.hasLength(title) || !StringUtils.hasLength(content)) {
            return Result.fail("标题或内容不能为空");
        }
        if (type == null) {
            type = 1;
        }
        Long id = notificationsService.publish(title, content, type);
        if (id != null) {
            return Result.success(id);
        }
        return Result.fail("发布通知失败");
    }

    /**
     * 获取通知列表
     */
    @RequestMapping("/list")
    public Result<List<NotificationsInfo>> list() {
        List<NotificationsInfo> notifications = notificationsService.list();
        return Result.success(notifications);
    }
}
