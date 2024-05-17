package com.news.controller.player;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.news.common.constant.enums.CacheTypeEnum;
import com.news.common.constant.enums.GenderEnum;
import com.news.common.exception.DataException;
import com.news.common.tools.CodeTools;
import com.news.common.tools.GenerateTools;
import com.news.entity.PlayerInfo;
import com.news.pojo.req.player.PlayerRegisterReq;
import com.news.pojo.resp.player.PlayerTokenResp;
import com.news.service.EhcacheService;
import com.news.service.PlayerAccountInventoryService;
import com.news.service.PlayerInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@Api(tags = "玩家")
@RequestMapping("/player/playerInfo")
public class PlayerInfoApi {

    @Autowired
    private PlayerAccountInventoryService playerAccountInventoryService;
    @Autowired
    private PlayerInfoService playerInfoService;
    @Autowired
    private EhcacheService ehcacheService;

    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "注册")
    public R<PlayerTokenResp> register(@RequestBody @Valid PlayerRegisterReq req) {
        log.info("玩家登录入参:{}", JSONUtil.toJsonStr(req));
        if (StringUtils.isBlank(req.getPhone()) && StringUtils.isBlank(req.getEmail())){
            throw new DataException("手机号与邮箱不能同时为空");
        }
        if (!req.getName().matches("^[a-zA-Z0-9]+([._ -]?[a-zA-Z0-9]+)*$")){
            throw new DataException("昵称仅支持一位.或_或-或空格的特殊符号");
        }
        if (!req.getPassword().matches("^[a-zA-Z0-9]+$")){
            throw new DataException("请输入由数字和字母组成的密码");
        }
        PlayerInfo playerInfo = playerInfoService.findByName(req.getName());
        if (playerInfo != null){
            throw new DataException("该昵称已被占用,请您另外输入");
        }
        if (StringUtils.isNotBlank(req.getPhone()) && req.getPhone().length() >= 20) {
            throw new DataException("手机号不能超20位");
        }
        if (StringUtils.isNotBlank(req.getEmail()) && req.getEmail().length() >= 30) {
            throw new DataException("邮箱不能超30位");
        }

        String salt = GenerateTools.getUUID();

        playerInfo = new PlayerInfo();
        playerInfo.setAccount(playerAccountInventoryService.getRandomAccount());
        playerInfo.setName(req.getName());
        playerInfo.setPassword(CodeTools.md5AndSalt(req.getPassword(), salt));
        playerInfo.setSalt(salt);
        playerInfo.setPhone(req.getPhone());
        playerInfo.setCreateName("玩家");
        playerInfo.setCreateTime(LocalDateTime.now());
        playerInfo.setBirth(req.getBirth());
        playerInfo.setGender(req.getGender());
        playerInfoService.save(playerInfo);

        String tokenId = GenerateTools.createTokenId();
        PlayerTokenResp playerTokenResp = BeanUtil.toBean(playerInfo, PlayerTokenResp.class);
        playerTokenResp.setTokenId(tokenId);
        playerTokenResp.setLoginTime(LocalDateTime.now());

        ehcacheService.getCache(CacheTypeEnum.PLAYER_TOKEN).put(tokenId, playerTokenResp);
        return R.ok(playerTokenResp);
    }

}
