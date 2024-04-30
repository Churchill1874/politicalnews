package com.news.pojo.req.blacklist;

import com.news.pojo.req.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BlacklistPage extends Page implements Serializable {

    private static final long serialVersionUID = 8526224475627286219L;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty("设备号")
    private String device;

}
