package com.news.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum SendTypeEnum {
    NO_TARGET(0,"无目标发言"),
    AT(1,"针对发言");


    @Getter
    @EnumValue
    @JsonValue
    private int code;

    @Getter
    private String name;

    SendTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }
}
