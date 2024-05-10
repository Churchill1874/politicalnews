package com.news.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.news.common.constant.enums.SendTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("聊天室记录")
@TableName("room_chat_record")
public class RoomChatRecord extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 5031950363378250707L;

    @TableField("account")
    @ApiModelProperty("发送人账号")
    private Integer account;

    @TableField("is_reply")
    @ApiModelProperty("是否是回复消息")
    private Boolean isReply;

    @TableField("reply_id")
    @ApiModelProperty("回复消息的id")
    private Long replyId;

    @TableField("reply_content")
    @ApiModelProperty("回复消息目标的内容 截图开始10个字 + ...")
    private String replyContent;

    @TableField("target_type")
    @ApiModelProperty("消息类型")
    private SendTypeEnum targetType;

    @TableField("target_account")
    @ApiModelProperty("目标账号")
    private Integer targetAccount;

    @TableField("target_read")
    @ApiModelProperty("目标是否已读")
    private Boolean targetRead;

    @TableField("message")
    @ApiModelProperty("消息内容")
    private String message;

}
