package com.news.pojo.resp.admin;

import com.news.common.constant.enums.ManageRoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AdminTokenResp implements Serializable {
    private static final long serialVersionUID = 2977509962919403140L;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("角色")
    private ManageRoleEnum role;

    @ApiModelProperty("登陆时间")
    private LocalDateTime loginTime;

    @ApiModelProperty("登录令牌")
    private String tokenId;

}
