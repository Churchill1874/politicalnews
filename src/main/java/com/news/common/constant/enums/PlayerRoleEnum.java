package com.news.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum PlayerRoleEnum {

    BOT(1, "机器人"),
    PLAYER(2, "玩家");

    @Getter
    @EnumValue
    @JsonValue
    private int code;

    @Getter
    private String name;

    PlayerRoleEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }


}
