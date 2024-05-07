package com.news.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.news.common.constant.enums.PlayerRoleEnum;
import com.news.common.constant.enums.UserStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@ApiModel("玩家")
@TableName("player_info")
public class PlayerInfo extends CreatorBaseInfo implements Serializable {
    private static final long serialVersionUID = 8299446727161357208L;

    @TableField("name")
    @ApiModelProperty("昵称")
    private String name;

    @TableField("birth")
    @ApiModelProperty("生日")
    private LocalDate birth;

    @TableField("account")
    @ApiModelProperty("账号")
    private String account;

    @TableField("password")
    @ApiModelProperty("密码")
    private String password;

    @TableField("level")
    @ApiModelProperty("等级")
    private Integer level;

    @TableField("phone")
    @ApiModelProperty("手机号")
    private String phone;

    @TableField("email")
    @ApiModelProperty("邮箱")
    private String email;

    @TableField("self_introduction")
    @ApiModelProperty("自我介绍")
    private String selfIntroduction;

    @TableField("role")
    @ApiModelProperty("角色")
    private PlayerRoleEnum role;

    @TableField("user_status")
    @ApiModelProperty("状态")
    private UserStatusEnum status;

    @TableField("is_custom_avatar")
    @ApiModelProperty("是否使用自定义头像")
    private Boolean isCustomAvatar;

    @TableField("avatar_number")
    @ApiModelProperty("公共头像编号")
    private String avatarNumber;

    @TableField("avatar_path")
    @ApiModelProperty("自定义头像路径")
    private String avatarPath;

}
