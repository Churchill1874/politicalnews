package com.news.common.aspect;

import com.news.common.constant.enums.UserStatusEnum;
import com.news.common.exception.AuthException;
import com.news.common.tools.TokenTools;
import com.news.pojo.resp.player.PlayerTokenResp;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PlayerLoginCheck {

    //定位切面的目标 是一个注解
    @Pointcut("@annotation(com.news.common.annotation.PlayerLoginCheck)")
    public void playerLoginCheck() {

    }

    @Before("playerLoginCheck()")
    public void beforeCut(JoinPoint joinPoint) {
        PlayerTokenResp playerTokenResp = TokenTools.getPlayerToken();
        if (playerTokenResp.getStatus() == UserStatusEnum.DISABLE) {
            throw new AuthException();
        }
    }

/*    @After("loginCheck()")
    public void afterCut(){

    }*/


}
