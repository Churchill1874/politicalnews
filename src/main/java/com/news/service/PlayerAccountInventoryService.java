package com.news.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.news.entity.PlayerAccountInventory;
import org.springframework.scheduling.annotation.Async;

public interface PlayerAccountInventoryService extends IService<PlayerAccountInventory> {

    //随机获取玩家账号
    int getRandomAccount();

    //账号库存补充定时任务
    @Async
    void insertTask();

}
