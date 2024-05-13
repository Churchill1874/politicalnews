package com.news.service.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.entity.PlayerAccountInventory;
import com.news.entity.PlayerInfo;
import com.news.mapper.PlayerAccountInventoryMapper;
import com.news.service.PlayerAccountInventoryService;
import com.news.service.PlayerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (randomAccount == null) {//如果账号库存用没了,就新增一批
            PlayerInfo playerInfo = playerInfoService.maxAccountPlayer();//从玩家表获取最大玩家账号 根据最大玩家账号 新创建100个账号

            if (playerInfo == null) { //如果还没有玩家账号存在 就从10001开始插入100条账号
                int START_ACCOUNT = 10001;//玩家的起始账号
                batchInsertAccount(START_ACCOUNT);
            } else {
                batchInsertAccount(playerInfo.getAccount() + 1);//否则就从玩家中最大账号+1 开始新增账号
            }

            randomAccount = playerAccountInventoryMapper.getRandomAccount();//再次获取账号库存的随机账号
        }

        //从账号库存删除随机出来的账号
        playerAccountInventoryMapper.deleteById(randomAccount.getId());
        return randomAccount.getAccount();
    }


    //根据起始账号 和 指定条数 批量插入账号
    private void batchInsertAccount(int startAccount) {
        List<PlayerAccountInventory> playerAccountInventoryList = new ArrayList<>();
        int INSERT_COUNT = 100;//每次插入账号库的条数
        for (int i = 0; i <= INSERT_COUNT; i++) {
            PlayerAccountInventory accountInventory = new PlayerAccountInventory();
            accountInventory.setAccount(startAccount);
            accountInventory.setCreateTime(LocalDateTime.now());
            playerAccountInventoryList.add(accountInventory);
        }
        //批量插入
        saveBatch(playerAccountInventoryList);
    }


}
