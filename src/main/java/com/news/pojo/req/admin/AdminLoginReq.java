package com.news.pojo.req.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AdminLoginReq implements Serializable {
    private static final long serialVersionUID = 1305416174505427537L;

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号", required = true)
    private String account;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true)
    private String verificationCode;

}
