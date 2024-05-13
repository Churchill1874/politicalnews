package com.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.news.entity.PlayerAccountInventory;
import org.apache.ibatis.annotations.Select;

public interface PlayerAccountInventoryMapper extends BaseMapper<PlayerAccountInventory> {

    @Select("SELECT * FROM PLAYER_ACCOUNT_INVENTORY ORDER BY RAND() LIMIT 1")
    PlayerAccountInventory getRandomAccount();

}
