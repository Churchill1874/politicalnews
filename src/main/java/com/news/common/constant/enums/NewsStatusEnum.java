package com.news.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum NewsStatusEnum {

    NORMAL(1,"普通"),
    TOP(2,"置顶"),
    HOT(3,"热门");


    @Getter
    @EnumValue
    private int code;

    @Getter
    @JsonValue
    private String name;

    NewsStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }
}
