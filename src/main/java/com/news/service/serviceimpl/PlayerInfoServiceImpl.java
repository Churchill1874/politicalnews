package com.news.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.entity.PlayerInfo;
import com.news.mapper.PlayerInfoMapper;
import com.news.pojo.req.PageBase;
import com.news.service.PlayerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlayerInfoServiceImpl extends ServiceImpl<PlayerInfoMapper, PlayerInfo> implements PlayerInfoService {

    @Override
    public IPage<PlayerInfo> queryPage(PlayerInfo playerInfo, PageBase pageBase) {
        IPage<PlayerInfo> iPage = new Page<>(pageBase.getPageNumber(), pageBase.getPageSize());
        QueryWrapper<PlayerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(playerInfo.getName()), PlayerInfo::getName, playerInfo.getName())
                .eq(StringUtils.isNotBlank(playerInfo.getAccount()), PlayerInfo::getAccount, playerInfo.getAccount())
                .eq(StringUtils.isNotBlank(playerInfo.getPhone()), PlayerInfo::getPhone, playerInfo.getPhone())
                .eq(StringUtils.isNotBlank(playerInfo.getEmail()), PlayerInfo::getEmail, playerInfo.getEmail())
                .eq(StringUtils.isNotBlank(playerInfo.getCity()), PlayerInfo::getCity, playerInfo.getCity())
                .eq(playerInfo.getGender() != null, PlayerInfo::getGender, playerInfo.getGender())
                .eq(playerInfo.getBirth() != null, PlayerInfo::getBirth, playerInfo.getBirth())
                .eq(playerInfo.getLevel() != null, PlayerInfo::getLevel, playerInfo.getLevel())
                .eq(playerInfo.getIsBot() != null, PlayerInfo::getIsBot, playerInfo.getIsBot())
                .eq(playerInfo.getStatus() != null, PlayerInfo::getStatus, playerInfo.getStatus())
                .eq(pageBase.getStartTime() != null, PlayerInfo::getCreateTime, pageBase.getStartTime())
                .eq(pageBase.getEndTime() != null, PlayerInfo::getCreateTime, pageBase.getEndTime())
                .orderByDesc(PlayerInfo::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public PlayerInfo findByAccount(String account) {
        QueryWrapper<PlayerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlayerInfo::getAccount, account);
        return getOne(queryWrapper);
    }

    @Override
    public void add(PlayerInfo playerInfo) {
        save(playerInfo);
    }

}
