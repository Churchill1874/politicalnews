package com.news.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.entity.RoomChatRecord;
import com.news.mapper.RoomChatRecordMapper;
import com.news.service.RoomChatRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class RoomChatRecordServiceImpl extends ServiceImpl<RoomChatRecordMapper, RoomChatRecord> implements RoomChatRecordService {

    @Override
    public void send(RoomChatRecord roomChatRecord) {
        //todo 待实现
    }

    @Override
    public void readStatus(Long id) {
        UpdateWrapper<RoomChatRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(RoomChatRecord::getTargetRead, true)
                .set(RoomChatRecord::getUpdateTime, LocalDateTime.now())
                .eq(RoomChatRecord::getId, id);
        update(updateWrapper);
    }

}
