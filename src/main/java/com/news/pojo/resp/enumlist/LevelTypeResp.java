package com.news.pojo.resp.enumlist;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LevelTypeResp implements Serializable {
    private static final long serialVersionUID = -1644281969101090629L;

    @ApiModelProperty("级别 注意:也是枚举映射请求入参")
    private int level;

    @ApiModelProperty("等级名称")
    private String name;

    @ApiModelProperty("需要发表评论总数量")
    private int commentCount;

    @ApiModelProperty("需要被赞评论总数量")
    private int likesReceivedCount;

    @ApiModelProperty("需要下注猜对总数量")
    private int correctCount;

}
