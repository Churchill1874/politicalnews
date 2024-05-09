package com.news.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum LevelTypeEnum {
    LEVEL_0(0,"新手小白",0,0,0),
    LEVEL_1(1,"暗中观察",1, 10,0),
    LEVEL_2(2,"热心观众",50,100,0),
    LEVEL_3(3,"点评员",200,1000,1),
    LEVEL_4(4,"评论家",500,10000,10),
    LEVEL_5(5,"资深评论家",3000,100000,100),
    LEVEL_6(6,"点评专家",5000,200000,500),
    LEVEL_7(7,"副谋士",6000,500000,800),
    LEVEL_8(8,"谋士",80000,600000,1000),
    LEVEL_9(9,"王佐谋士",10000,1000000,5000),
    LEVEL_10(10,"尚书令",20000,2000000,8000),
    LEVEL_11(11,"军师祭酒",50000,5000000,10000);

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
