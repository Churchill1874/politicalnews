package com.news.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SuperAdminCheck {

    //定位切面的目标 是一个注解
    @Pointcut("@annotation(com.news.common.annotation.SuperAdminLoginCheck)")
    public void superAdminLoginCheck() {

    }

    @Before("superAdminLoginCheck()")
    public void beforeCut(JoinPoint joinPoint) {
/*        AdminToken adminToken = TokenTools.getAdminToken();
        if (adminToken.getRole() != RoleEnum.SUPER_ADMIN) {
            throw new AuthException();
        }*/
    }


}
