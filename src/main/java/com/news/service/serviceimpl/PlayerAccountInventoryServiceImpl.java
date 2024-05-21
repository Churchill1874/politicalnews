package com.news.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
    private void batchInsertAccount(int startAccount, int insertCount) {
        List<PlayerAccountInventory> playerAccountInventoryList = new ArrayList<>();

        for (int i = 0; i < insertCount; i++) {
            PlayerAccountInventory accountInventory = new PlayerAccountInventory();
            accountInventory.setAccount(startAccount + i);
            accountInventory.setCreateTime(LocalDateTime.now());
            playerAccountInventoryList.add(accountInventory);
        }

        //批量插入
        saveBatch(playerAccountInventoryList);
    }

    @Override
    public void startUpInsertInventory() {
        LocalDateTime startTime = LocalDateTime.now();//计算开始时间

        int initAccount = 10001;//初始化账号从多少开始
        int lessThanCount = 10000;//少于多少后开始补库
        int insertCount = 90000;//每次插入多少库存账号


        int inventoryCount = count();//账号库总条数


        if (inventoryCount == 0) {//如果账号库彻底被玩家用尽
            PlayerInfo playerInfo = playerInfoService.maxAccountPlayer();//查询最大的玩家账号
            if (playerInfo == null) {//目前一个玩家没有的情况
                batchInsertAccount(initAccount, insertCount);//玩家账号从10001开始
                log.info("初始批量插入账号库,使用时间:{} 毫秒", Duration.between(startTime, LocalDateTime.now()).toMillis());
            }
            if (playerInfo != null) {//如果目前最大账号的玩家不为空 从最大账号的下一位账号开始批量增加账号库
                batchInsertAccount(playerInfo.getAccount() + 1, insertCount);
                log.info("账号库用空,从账号 {} 开始批量补库,使用时间:{} 毫秒", playerInfo.getAccount() + 1, Duration.between(startTime, LocalDateTime.now()).toMillis());
            }
        }


        if (inventoryCount > 0 && inventoryCount <= lessThanCount) {//如果账号库还有账号,但是小于等于10000条
            IPage<PlayerAccountInventory> iPage = new Page<>(1, 1);
            QueryWrapper<PlayerAccountInventory> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().orderByDesc(PlayerAccountInventory::getAccount);
            iPage = page(iPage, queryWrapper);//分页查出库存最大账号 利用账号降序
            int maxInventoryAccount = iPage.getRecords().get(0).getAccount();//最大的库存账号

            PlayerInfo playerInfo = playerInfoService.maxAccountPlayer();//最大账号的玩家
            int maxPlayerAccount = playerInfo == null ? 0 : playerInfo.getAccount();

            int maxAccount = Math.max(maxInventoryAccount, maxPlayerAccount); //哪个帐号大就用哪个账号开始批量增加账号库
            batchInsertAccount(maxAccount + 1, insertCount);
            log.info("账号库小于等于10000条,批量补库,使用时间:{} 毫秒", Duration.between(startTime, LocalDateTime.now()).toMillis());
        }
    }

}
