package com.news.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum LevelTypeEnum {
    LEVEL_0(0,"暗中观察",0,0,0),
    LEVEL_1(1,"新人小白",1, 10,0),
    LEVEL_2(2,"围观群众",50,100,0),
    LEVEL_3(3,"热心市民",100,1000,0),
    LEVEL_4(4,"社会长者",200,10000,1),
    LEVEL_5(5,"民间智者",300,100000,10),
    LEVEL_6(6,"在野政客",500,500000,50),
    LEVEL_7(7,"江湖名宿",500,1000000,100),
    LEVEL_8(8,"智囊",800,3000000,300),
    LEVEL_9(9,"谋士",1000,5000000,1000),
    LEVEL_10(10,"军师",2000,10000000,2000),
    LEVEL_11(11,"帝师",3000,20000000,3000),
    LEVEL_12(12,"王佐",3000,30000000,5000);

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
