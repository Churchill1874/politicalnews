package com.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.news.entity.RoomChatRecord;

/**
 * 聊天室记录
 */
public interface RoomChatRecordService extends IService<RoomChatRecord> {

    /**
     * 发送消息
     * @param roomChatRecord
     */
    void send(RoomChatRecord roomChatRecord);

    /**
     * 修改消息被@人已读状态
     * @param id
     */
    void readStatus(Long id);

}
