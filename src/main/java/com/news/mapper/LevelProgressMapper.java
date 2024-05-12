package com.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.news.entity.LevelProgress;
import org.apache.ibatis.annotations.Update;

public interface LevelProgressMapper extends BaseMapper<LevelProgress> {

    @Update("UPDATE level_progress SET comment_count = comment_count + 1 WHERE account = #{account}")
    void addCommentCount(Integer account);

    @Update("UPDATE level_progress SET likes_received_count = likes_received_count + 1 WHERE account = #{account}")
    void addLikesReceivedCount(Integer account);

    @Update("UPDATE level_progress SET correct_count = correct_count + 1 WHERE account = #{account}")
    void addCorrectCount(Integer account);

}
