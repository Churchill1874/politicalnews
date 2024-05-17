package com.news.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.common.exception.DataException;
import com.news.entity.PlayerAccountInventory;
import com.news.entity.PlayerInfo;
import com.news.mapper.PlayerAccountInventoryMapper;
import com.news.service.PlayerAccountInventoryService;
import com.news.service.PlayerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PlayerAccountInventoryServiceImpl extends ServiceImpl<PlayerAccountInventoryMapper, PlayerAccountInventory> implements PlayerAccountInventoryService {

    @Autowired
    private PlayerInfoService playerInfoService;
    @Autowired
    private PlayerAccountInventoryMapper playerAccountInventoryMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized int getRandomAccount() {
        PlayerAccountInventory randomAccount = playerAccountInventoryMapper.getRandomAccount();//随机获取玩家库存账号

        if (randomAccount == null) {//如果账号库存用没了 抛出异常联系客服
            log.error("账号库用空,需要新建一批");
            throw new DataException("当前注册人数过多,请联系客服,或稍后再次尝试");
        }

        playerAccountInventoryMapper.deleteById(randomAccount.getId());//从账号库存删除随机出来的账号
        return randomAccount.getAccount();
    }

    //根据起始账号 和 指定条数 批量插入账号
    private void batchInsertAccount(int startAccount) {
        List<PlayerAccountInventory> playerAccountInventoryList = new ArrayList<>();
        int INSERT_COUNT = 1000;//每次插入账号库的条数

        for (int i = 0; i < INSERT_COUNT; i++) {
            PlayerAccountInventory accountInventory = new PlayerAccountInventory();
            accountInventory.setAccount(startAccount + i);
            accountInventory.setCreateTime(LocalDateTime.now());
            playerAccountInventoryList.add(accountInventory);
        }

        //批量插入
        saveBatch(playerAccountInventoryList);
    }

    @Async
    @Override
    public void insertTask() {
        LocalDateTime startTime = LocalDateTime.now();//计算开始时间

        QueryWrapper<PlayerAccountInventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(PlayerAccountInventory::getAccount);
        List<PlayerAccountInventory> playerAccountInventoryList = list(queryWrapper);//获取所有账号库存 并且按照账号大小降序


        if (CollectionUtils.isEmpty(playerAccountInventoryList)) {//如果账号库空了
            PlayerInfo playerInfo = playerInfoService.maxAccountPlayer();//查询最大的玩家账号
            if (playerInfo == null) {//目前一个玩家没有的情况
                batchInsertAccount(20000);//玩家账号从20000开始
                log.info("初始批量插入账号库,使用时间:{} 毫秒", Duration.between(startTime, LocalDateTime.now()).toMillis());
            }
            if (playerInfo != null) {//如果目前最大账号的玩家不为空 从最大账号的下一位账号开始批量增加账号库
                batchInsertAccount(playerInfo.getAccount() + 1);
                log.info("账号库用空,从账号 {} 开始批量补库,使用时间:{} 毫秒", playerInfo.getAccount() + 1, Duration.between(startTime, LocalDateTime.now()).toMillis());
            }
        }


        if (CollectionUtils.isNotEmpty(playerAccountInventoryList) && playerAccountInventoryList.size() <= 100) {//如果账号库还有账号,但是小于等于100条
            PlayerAccountInventory maxAccount = playerAccountInventoryList.get(0);
            batchInsertAccount(maxAccount.getAccount() + 1);
            log.info("账号库小于等于100条,批量补库,使用时间:{} 毫秒", Duration.between(startTime, LocalDateTime.now()).toMillis());
        }
    }

}
