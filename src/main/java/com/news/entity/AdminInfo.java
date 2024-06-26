package com.news.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.news.common.constant.enums.ManageRoleEnum;
import com.news.common.constant.enums.UserStatusEnum;
import com.news.entity.base.CreatorBaseInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("管理员")
@TableName("admin_info")
public class AdminInfo extends CreatorBaseInfo implements Serializable {
    private static final long serialVersionUID = 1451452658785358040L;

    @TableField("account")
    @ApiModelProperty("账号")
    private String account;

    @TableField("name")
    @ApiModelProperty("昵称")
    private String name;

    @TableField("password")
    @ApiModelProperty("密码")
    private String password;

    @TableField("role")
    @ApiModelProperty("角色")
    private ManageRoleEnum role;

    @TableField("status")
    @ApiModelProperty("状态")
    private UserStatusEnum status;

    @TableField("salt")
    @ApiModelProperty("盐")
    private String salt;

}
