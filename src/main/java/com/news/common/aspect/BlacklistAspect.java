package com.news.common.aspect;

import com.news.common.exception.IpException;
import com.news.common.tools.HttpTools;
import com.news.entity.Blacklist;
import com.news.service.BlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Aspect
@Component
public class BlacklistAspect {

    @Autowired
    private BlacklistService blacklistService;

    @Pointcut("execution(* com.news.controller.*.*.*(..))")
    public void blacklistPointCut() {
    }

    @Before("blacklistPointCut()")
    public void beforeExecute() {
        Set<String> blacklistIpSet = blacklistService.getIpSet();
        if (blacklistIpSet.contains(HttpTools.getIp())){
            throw new IpException();
        }
    }

}
