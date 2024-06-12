package com.news.service.serviceimpl;

import com.news.common.constant.CacheKeyConstant;
import com.news.common.exception.DataException;
import com.news.common.exception.IpException;
import com.news.common.tools.GenerateTools;
import com.news.common.tools.HttpTools;
import com.news.entity.Blacklist;
import com.news.pojo.resp.admin.AdminTokenResp;
import com.news.pojo.resp.player.PlayerTokenResp;
import com.news.service.BlacklistService;
import com.news.service.EhcacheService;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 该类对ehcache.xml配置文件里面已经配置的缓存容器进行实现获取，方便使用
 */
@Slf4j
@Service
public class EhcacheServiceImpl implements EhcacheService {

    @Autowired
    private BlacklistService blacklistService;
    @Autowired
    private CacheManager cacheManager;

    @Override
    public void checkIp3SecondsClick(Integer limitCount, String remarks) {
        String ip = HttpTools.getIp();
        org.ehcache.Cache<String, Integer> cache = lock3SecondCache();
        Integer reqCount =  cache.get(ip);

        if (reqCount != null) {
            if (reqCount >= limitCount) {
                //如果ip存在黑名单就更新时间
                Blacklist blacklist = blacklistService.findByIp(ip);
                if (blacklist != null) {
                    blacklist.setUpdateTime(LocalDateTime.now());
                    blacklist.setUpdateName("系统");
                    blacklistService.updateById(blacklist);
                } else { //否则就新增
                    blacklist = new Blacklist();
                    blacklist.setIp(ip);
                    blacklist.setRemarks(remarks);
                    blacklist.setCreateName("系统");
                    blacklistService.insert(blacklist);
                }
                throw new IpException();
            } else {
                cache.put(ip, reqCount + 1);
            }
        } else {
            cache.put(ip, 1);
        }
    }


    @Override
    public Cache<String, Integer> lock3SecondCache() {
        return cacheManager.getCache(CacheKeyConstant.LOCK_3_SECOND, String.class, Integer.class);
    }

    @Override
    public Cache<String, String> verificationCache() {
        return cacheManager.getCache(CacheKeyConstant.VERIFICATION_CODE, String.class, String.class);
    }

    @Override
    public Cache<String, AdminTokenResp> adminTokenCache() {
        return cacheManager.getCache(CacheKeyConstant.ADMIN_TOKEN, String.class, AdminTokenResp.class);
    }

    @Override
    public Cache<String, PlayerTokenResp> playerTokenCache() {
        return cacheManager.getCache(CacheKeyConstant.PLAYER_TOKEN, String.class, PlayerTokenResp.class);
    }

    @Override
    public Cache<String, PlayerTokenResp> onlineCount() {
        return cacheManager.getCache(CacheKeyConstant.ONLINE_COUNT, String.class, PlayerTokenResp.class);
    }

    @Override
    public String getVC(String key, Integer limitCount, String remarks) {
        //添加频繁点击校验 3秒内点击超过30次 检查警告日志 如果该ip已经存在警告则拉黑 不存在则新加警告日志
        this.checkIp3SecondsClick(limitCount, remarks);

        //获取验证码
        String codeImageStream = null;
        String code = null;
        try {
            code = GenerateTools.getCaptchaText(5);
            codeImageStream = GenerateTools.getCaptchaImage(code);
        } catch (IOException e) {
            log.error("生成验证码异常:{}", e.getMessage());
            throw new DataException(e.getMessage());
        }

        verificationCache().put(key, code);
        return codeImageStream;
    }

    @Override
    public Set<String> getBlacklistIpSetCache() {
        Cache<String, Set<String>> cache = cacheManager.getCache(CacheKeyConstant.BLACKLIST, String.class, (Class<Set<String>>)(Class<?>)Set.class);
        return cache.get(CacheKeyConstant.BLACKLIST_SET_KEY);
    }

    @Override
    public void setBlacklistIpSetCache(Set<String> blacklistIpSet) {
        cacheManager.getCache(CacheKeyConstant.BLACKLIST, String.class, (Class<Set<String>>)(Class<?>)Set.class)
                .put(CacheKeyConstant.BLACKLIST_SET_KEY, blacklistIpSet);
    }
}
