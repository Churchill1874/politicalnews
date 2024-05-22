package com.news.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.news.entity.PlayerAccountInventory;

public interface PlayerAccountInventoryService extends IService<PlayerAccountInventory> {

    //随机获取玩家账号
    int getRandomAccount();

    //账号库存补充定时任务
    void startUpInsertInventory();

}
