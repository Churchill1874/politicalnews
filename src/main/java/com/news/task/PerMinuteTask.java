package com.news.task;

import com.news.service.PlayerAccountInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 定时任务类
 */
@Slf4j
@Component
public class PerMinuteTask {

    @Autowired
    private PlayerAccountInventoryService playerAccountInventoryService;

    /**
     * 每分钟执行定时任务
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void perMinute() {
        LocalDateTime currentTime = LocalDateTime.now();//当前时间
        int hour = currentTime.getHour();//当前时
        int minute = currentTime.getMinute();//分钟

        //每10分钟执行一次的任务
        if (minute % 10 == 0){
        }

    }

}