package com.news.common.tools;

import com.news.common.constant.enums.CacheTypeEnum;
import com.news.common.exception.TokenException;
import com.news.pojo.resp.admin.AdminTokenResp;
import com.news.pojo.resp.player.PlayerTokenResp;
import com.news.service.EhcacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenTools {

    private static EhcacheService ehcacheService;

    //因为static修饰成员变量不支持自动注入 所以以下面方式给静态成员注入
    @Autowired
    public void setEhcacheService(EhcacheService ehcacheService) {
        TokenTools.ehcacheService = ehcacheService;
    }

    /**
     * 获取管理员登录信息
     * @return
     */
    public static AdminTokenResp getAdminToken() {
        AdminTokenResp adminTokenResp = ehcacheService.getCache(CacheTypeEnum.ADMIN_TOKEN).get(HttpTools.getHeaderToken(), AdminTokenResp.class);
        if (adminTokenResp == null) {
            throw new TokenException();
        }
        return adminTokenResp;
    }


    /**
     * 获取管理员登录信息
     * @return
     */
    public static PlayerTokenResp getPlayerToken() {
        PlayerTokenResp playerTokenResp = ehcacheService.getCache(CacheTypeEnum.PLAYER_TOKEN).get(HttpTools.getHeaderToken(), PlayerTokenResp.class);
        if (playerTokenResp == null) {
            throw new TokenException();
        }
        return playerTokenResp;
    }


}
