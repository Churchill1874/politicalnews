package com.news.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("账号库存")
@TableName("player_account_inventory")
public class PlayerAccountInventory implements Serializable {
    private static final long serialVersionUID = 2958831752897380339L;

    @TableField("id")
    @ApiModelProperty("id")
    private Integer id;

    @TableField("account")
    @ApiModelProperty("账号")
    private Integer account;

    @TableField("create_time")
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

}
