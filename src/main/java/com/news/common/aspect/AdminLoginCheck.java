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
public class AdminLoginCheck {

    //定位切面的目标 是一个注解
    @Pointcut("@annotation(com.news.common.annotation.AdminLoginCheck)")
    public void adminLoginCheck() {

    }

    @Before("adminLoginCheck()")
    public void beforeCut(JoinPoint joinPoint) {
/*        Token token = TokenTools.getToken();
        if ((token.getRole() != RoleEnum.ADMIN.getCode() && token.getRole() != RoleEnum.SUPER_ADMIN.getCode())
                || token.getStatus() == UserStatusEnum.DISABLE.getCode()) {
            throw new AuthException();
        }*/
    }

/*    @After("loginCheck()")
    public void afterCut(){

    }*/


}
