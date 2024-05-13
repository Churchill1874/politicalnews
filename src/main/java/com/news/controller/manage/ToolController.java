package com.news.controller.manage;

import com.baomidou.mybatisplus.extension.api.R;
import com.news.common.constant.enums.LevelTypeEnum;
import com.news.pojo.resp.enumlist.LevelTypeResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "工具类")
@RequestMapping("/manage/tools")
public class ToolController {

    @ApiModelProperty("玩家等级枚举列表")
    @PostMapping("/enum/playerLevel")
    public R<List<LevelTypeResp>> playerLevel() {
        List<LevelTypeResp> levelTypeRespList = new ArrayList<>();
        for(LevelTypeEnum levelTypeEnum: LevelTypeEnum.values()){
            LevelTypeResp levelTypeResp = new LevelTypeResp();
            levelTypeResp.setLevel(levelTypeEnum.getCode());
            levelTypeResp.setName(levelTypeEnum.getName());
            levelTypeResp.setCommentCount(levelTypeEnum.getCommentCount());
            levelTypeResp.setLikesReceivedCount(levelTypeEnum.getLikesReceivedCount());
            levelTypeResp.setCorrectCount(levelTypeEnum.getCorrectCount());
            levelTypeRespList.add(levelTypeResp);
        }
        return R.ok(levelTypeRespList);
    }


}
