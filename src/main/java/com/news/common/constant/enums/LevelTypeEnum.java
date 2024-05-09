package com.news.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum LevelTypeEnum {
    LEVEL_0(0,"暗中观察",0,0,0),
    LEVEL_1(1,"热心民众",1, 10,0),
    LEVEL_2(2,"民间智者",50,100,0),
    LEVEL_3(3,"江湖隐士",200,1000,1),
    LEVEL_4(4,"政客人才",500,10000,10),
    LEVEL_5(5,"职业政客",3000,100000,50),
    LEVEL_6(6,"智囊专家",5000,200000,300),
    LEVEL_7(7,"副谋士",6000,300000,500),
    LEVEL_8(8,"谋士",80000,600000,1000),
    LEVEL_9(9,"王佐谋士",10000,1000000,5000),
    LEVEL_10(10,"军师祭酒",20000,2000000,8000),
    LEVEL_11(11,"尚书令",50000,5000000,10000),
    LEVEL_12(12,"军师中郎将",80000,8000000,20000);

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
