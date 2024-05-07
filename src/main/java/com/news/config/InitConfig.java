package com.news.config;

import com.news.common.constant.enums.ManageRoleEnum;
import com.news.entity.AdminInfo;
import com.news.service.AdminInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Slf4j
@Component
public class InitConfig {

    //超管管理员账号
    private static final String SUPER_ADMIN_ACCOUNT = "10000";

    private static final String PASSWORD = "111111a";

    //获取创建机器人开关
    @Value("${init.create.bot}")
    private boolean createBot;

    @Autowired
    private AdminInfoService adminInfoService;

    /**
     * 项目启动时运行方法
     */
    @PostConstruct
    private void run() {
        AdminInfo adminInfo = adminInfoService.findByAccount(SUPER_ADMIN_ACCOUNT);
        if (adminInfo == null){
            adminInfo = new AdminInfo();
            adminInfo.setAccount(SUPER_ADMIN_ACCOUNT);
            adminInfo.setName("超级管理员");
            adminInfo.setPassword(PASSWORD);
            adminInfo.setRole(ManageRoleEnum.SUPER_ADMIN);
            adminInfo.setCreateName("系统");
            adminInfo.setCreateTime(LocalDateTime.now());
            adminInfoService.save(adminInfo);
            log.info("初始化创建了超级管理员");
        }
    }


}
