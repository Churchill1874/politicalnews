package com.news.service;

import com.news.common.constant.enums.CacheTypeEnum;
import org.springframework.cache.Cache;

/**
 * 缓存服务
 */
public interface EhcacheService {

    /**
     * 根据类型获取缓存
     * @param cacheTypeEnum
     * @return
     */
    Cache getCache(CacheTypeEnum cacheTypeEnum);

    /**
     * 校验ip 3秒内频繁点击超过指定次数
     *
     * @param limitCount
     * @return
     */
    boolean checkIp3SecondsClick(Integer limitCount, String remarks);

    /**
     * 获取验证码 并设置每3秒的限制请求次数 和提示语
     * @param limitCount
     * @param remarks
     * @return
     */
    String getVC(String key, Integer limitCount, String remarks);

}
