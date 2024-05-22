package com.news.config;

import com.news.common.constant.enums.ManageRoleEnum;
import com.news.common.constant.enums.UserStatusEnum;
import com.news.common.tools.CodeTools;
import com.news.common.tools.GenerateTools;
import com.news.entity.AdminInfo;
import com.news.service.AdminInfoService;
import com.news.service.PlayerAccountInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Slf4j
@Component
public class InitConfig {


    @Value("${init.create.bot}")
    private boolean createBot;    //获取创建机器人开关

    @Autowired
    private AdminInfoService adminInfoService;
    @Autowired
    private PlayerAccountInventoryService playerAccountInventoryService;

    /**
     * 项目启动时运行方法
     */
    @PostConstruct
    private void run() {
        //生成超级管理员
        adminInfoService.initSuperAccount();
        //生成玩家账号库
        playerAccountInventoryService.startUpInsertInventory();
    }


}
