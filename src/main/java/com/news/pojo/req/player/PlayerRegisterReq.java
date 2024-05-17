package com.news.pojo.req.player;

import com.news.common.constant.enums.GenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@ApiModel("玩家注册信息")
public class PlayerRegisterReq implements Serializable {
    private static final long serialVersionUID = 3754040536204734634L;

    @NotBlank(message = "请输入昵称")
    @Length(max = 20, message = "请输入20位以内的昵称")
    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @NotBlank(message = "请输入密码")
    @Length(max = 20, message = "请输入20位以内的密码")
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("生日")
    private LocalDate birth;

    @ApiModelProperty("性别")
    private GenderEnum gender;


}
