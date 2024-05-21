package com.news.pojo.resp.player;

import com.news.common.constant.enums.UserStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("玩家token")
public class PlayerTokenResp implements Serializable {
    private static final long serialVersionUID = -773689514264864093L;

    @ApiModelProperty("令牌")
    private String tokenId;
    @ApiModelProperty("账号")
    private Integer account;
    @ApiModelProperty("昵称")
    private String name;
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("状态")
    private UserStatusEnum status;
    @ApiModelProperty("登陆时间")
    private LocalDateTime loginTime;


}
