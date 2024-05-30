package com.news.config;

import com.news.service.AdminInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class InitConfig {


    @Value("${init.create.bot}")
    private boolean createBot;    //获取创建机器人开关

    @Autowired
    private AdminInfoService adminInfoService;

    /**
     * 项目启动时运行方法
     */
    @PostConstruct
    private void run() {
        //生成超级管理员
        adminInfoService.initSuperAccount();
    }


}
