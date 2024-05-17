package com.news.common.aspect;

import com.news.common.constant.enums.ManageRoleEnum;
import com.news.common.constant.enums.UserStatusEnum;
import com.news.common.exception.AccountDisableException;
import com.news.common.exception.AuthException;
import com.news.common.exception.TokenException;
import com.news.common.tools.TokenTools;
import com.news.entity.AdminInfo;
import com.news.pojo.resp.admin.AdminTokenResp;
import com.news.service.AdminInfoService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AdminLoginCheck {

    @Autowired
    private AdminInfoService adminInfoService;


    //定位切面的目标 是一个注解
    @Pointcut("@annotation(com.news.common.annotation.AdminLoginCheck)")
    public void adminLoginCheck() {

    }

    @Before("adminLoginCheck()")
    public void beforeCut(JoinPoint joinPoint) {
        AdminTokenResp adminTokenResp = TokenTools.getAdminToken();
        if ((adminTokenResp.getRole() != ManageRoleEnum.ADMIN && adminTokenResp.getRole() != ManageRoleEnum.SUPER_ADMIN)) {
            throw new AuthException();
        }

        AdminInfo adminInfo = adminInfoService.findByAccount(adminTokenResp.getAccount());
        if (adminInfo.getStatus() == UserStatusEnum.DISABLE){
            throw new AccountDisableException();
        }

    }

/*    @After("loginCheck()")
    public void afterCut(){

    }*/


}
