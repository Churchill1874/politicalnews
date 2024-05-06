package com.news.service.serviceimpl;

import com.news.common.constant.enums.CacheTypeEnum;
import com.news.common.tools.GenerateTools;
import com.news.common.tools.HttpTools;
import com.news.entity.Blacklist;
import com.news.service.BlacklistService;
import com.news.service.EhcacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * 该类对ehcache.xml配置文件里面已经配置的缓存容器进行实现获取，方便使用
 */
@Service
public class EhcacheServiceImpl implements EhcacheService {

    @Autowired
    private BlacklistService blacklistService;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Cache getCache(CacheTypeEnum cacheTypeEnum) {
        if (CacheTypeEnum.PLAYER_TOKEN == cacheTypeEnum){
            return cacheManager.getCache("player_token");
        }
        if (CacheTypeEnum.ADMIN_TOKEN == cacheTypeEnum){
            return cacheManager.getCache("admin_token");
        }
        if (CacheTypeEnum.LOCK_3_SECOND == cacheTypeEnum){
            return cacheManager.getCache("lock_3_Second");
        }
        if (CacheTypeEnum.VERIFICATION_CODE == cacheTypeEnum){
            return cacheManager.getCache("verification_code");
        }
        if (CacheTypeEnum.BLACKLIST == cacheTypeEnum){
            return cacheManager.getCache("blacklist");
        }
        return null;
    }

    @Override
    public String getString(CacheTypeEnum cacheTypeEnum, String key) {
        Cache cache = getCache(cacheTypeEnum);
        if (cache == null){
            return null;
        }
        return cache.get(key, String.class);
    }

    @Override
    public boolean checkIp3SecondsClick(Integer limitCount, String remarks) {
        String ip = HttpTools.getIp();
        Cache cache = this.getCache(CacheTypeEnum.LOCK_3_SECOND);
        Integer reqCount = cache.get(ip, Integer.class);
        if (reqCount != null) {
            if (reqCount >= limitCount) {
                Blacklist blacklist = new Blacklist();
                blacklist.setIp(ip);
                blacklist.setRemarks(remarks);
                blacklistService.insert(blacklist);
                return true;
            } else {
                cache.put(ip, reqCount + 1);
            }
        } else {
            cache.put(ip, 1);
        }
        return false;
    }

    @Override
    public String getVC(String key, Integer limitCount, String remarks) {
        //添加频繁点击校验 3秒内点击超过30次 检查警告日志 如果该ip已经存在警告则拉黑 不存在则新加警告日志
        this.checkIp3SecondsClick(limitCount,remarks);
        //获取验证码
        String verificationCode = GenerateTools.getVerificationCode();
        this.getCache(CacheTypeEnum.VERIFICATION_CODE).put(key, verificationCode);

        return verificationCode;
    }


}
