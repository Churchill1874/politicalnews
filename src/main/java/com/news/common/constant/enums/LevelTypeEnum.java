package com.news.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum LevelTypeEnum {
    LEVEL_0(0,"新人小白",0,0,0),
    LEVEL_1(1,"暗中观察",1, 10,0),
    LEVEL_2(2,"围观群众",20,100,0),
    LEVEL_3(3,"热心民众",50,1000,0),
    LEVEL_4(4,"社会长者",100,10000,1),
    LEVEL_5(5,"民间智者",200,500000,10),
    LEVEL_6(6,"江湖名宿",300,800000,50),
    LEVEL_7(7,"政客",500,1000000,100),
    LEVEL_8(8,"智囊",800,2000000,200),
    LEVEL_9(9,"谋士",1000,3000000,500),
    LEVEL_10(10,"军师",2000,5000000,1000),
    LEVEL_11(11,"王佐",3000,8000000,3000);

    @Getter
    @EnumValue
    @JsonValue
    private int code;

    @Getter
    private String name;

    //新闻评论次数
    @Getter
    private int commentCount;

    //评论被点赞次数
    @Getter
    private int likesReceived;

    //新闻正确下注猜对结果次数
    @Getter
    private int correctCount;

    LevelTypeEnum(int code, String name,int commentCount, int likesReceived, int correctCount) {
        this.code = code;
        this.name = name;
        this.commentCount = commentCount;
        this.likesReceived = likesReceived;
        this.correctCount = correctCount;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }

}
