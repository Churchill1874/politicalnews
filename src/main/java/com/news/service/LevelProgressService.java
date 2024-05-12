package com.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.news.entity.LevelProgress;

/**
 * 等级进度
 */
public interface LevelProgressService extends IService<LevelProgress> {

    void addCommentCount(Integer account);

    void addLikesReceivedCount(Integer account);

    void addCorrectCount(Integer account);

}
