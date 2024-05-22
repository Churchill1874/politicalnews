package com.news.pojo.req.player;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel("玩家登录对象")
public class PlayerLoginReq implements Serializable {
    private static final long serialVersionUID = -713458171533157118L;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("账号")
    private Integer account;

    @NotBlank(message = "请输入密码")
    @Length(max = 20, message = "请输入20位以内的密码")
    @ApiModelProperty(value = "密码", required = true)
    private String password;


}
