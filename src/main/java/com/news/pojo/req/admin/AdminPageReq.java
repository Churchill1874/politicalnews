package com.news.pojo.req.admin;

import com.news.common.constant.enums.ManageRoleEnum;
import com.news.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdminPageReq extends PageBase implements Serializable {
    private static final long serialVersionUID = -3541694764459319409L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("角色")
    private ManageRoleEnum role;

}
