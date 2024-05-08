package com.news.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.news.entity.PlayerInfo;
import com.news.pojo.req.PageBase;

public interface PlayerInfoService extends IService<PlayerInfo> {


    IPage<PlayerInfo> queryPage(PlayerInfo playerInfo, PageBase pageBase);

    void add(PlayerInfo playerInfo);

}
