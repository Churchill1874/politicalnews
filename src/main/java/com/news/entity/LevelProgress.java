package com.news.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.news.entity.base.CreatorBaseInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("等级进度")
@TableName("level_progress")
public class LevelProgress extends CreatorBaseInfo implements Serializable {
    private static final long serialVersionUID = 3481531719646209790L;

    @TableField("account")
    @ApiModelProperty("账号")
    private String account;

    @TableField("comment_count")
    @ApiModelProperty("评论次数")
    private Integer commentCount;

    @TableField("likes_received_count")
    @ApiModelProperty("被赞次数")
    private Integer likesReceivedCount;

    @TableField("correct_count")
    @ApiModelProperty("下注正确次数")
    private Integer correctCount;

}