package com.news.service.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.entity.LevelProgress;
import com.news.mapper.LevelProgressMapper;
import com.news.service.LevelProgressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LevelProgressServiceImpl extends ServiceImpl<LevelProgressMapper, LevelProgress> implements LevelProgressService {

    @Autowired
    private LevelProgressMapper levelProgressMapper;

    @Override
    public void addCommentCount(Integer account) {
        levelProgressMapper.addCommentCount(account);
    }

    @Override
    public void addLikesReceivedCount(Integer account) {
        levelProgressMapper.addLikesReceivedCount(account);
    }

    @Override
    public void addCorrectCount(Integer account) {
        levelProgressMapper.addCorrectCount(account);
    }

}
