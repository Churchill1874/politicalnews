package com.news.controller.player;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.news.common.exception.AccountOrPasswordException;
import com.news.common.exception.DataException;
import com.news.common.tools.CodeTools;
import com.news.common.tools.GenerateTools;
import com.news.common.tools.HttpTools;
import com.news.entity.PlayerInfo;
import com.news.pojo.req.player.PlayerLoginReq;
import com.news.pojo.req.player.PlayerRegisterReq;
import com.news.pojo.resp.player.PlayerTokenResp;
import com.news.service.EhcacheService;
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
import java.time.LocalDateTime;

@Slf4j
@RestController
@Api(tags = "玩家")
@RequestMapping("/player/playerInfo")
public class PlayerInfoApi {
    @Autowired
    private PlayerInfoService playerInfoService;
    @Autowired
    private EhcacheService ehcacheService;


    private void checkRegisterPhone(String phone) {
        if (StringUtils.isNotBlank(phone) && phone.length() >= 20) {
            throw new DataException("手机号不能超20位");
        }
        if (StringUtils.isNotBlank(phone)) {
            PlayerInfo playerInfo = playerInfoService.findByLogin(null, null, phone, null);
            if (playerInfo != null) {
                throw new DataException("手机已被使用注册");
            }
        }
    }

    private void checkRegisterName(String name) {
        if (!name.matches("^[a-zA-Z0-9]+([._ -]?[a-zA-Z0-9]+)*$")) {
            throw new DataException("昵称仅支持一位.或_或-或空格的特殊符号");
        }
        PlayerInfo playerInfo = playerInfoService.findByName(name);
        if (playerInfo != null) {
            throw new DataException("该昵称已被占用,请您另外输入");
        }
    }

    private void checkRegisterEmail(String email) {
        if (StringUtils.isNotBlank(email) && email.length() >= 30) {
            throw new DataException("邮箱不能超30位");
        }
        if (StringUtils.isNotBlank(email)) {
            PlayerInfo playerInfo = playerInfoService.findByLogin(null, null, null, email);
            if (playerInfo != null) {
                throw new DataException("邮箱已被占用");
            }
        }
    }

    private void checkPasswordRules(String password){
        if (!password.matches("^[a-zA-Z0-9]+$")) {
            throw new DataException("请输入由数字和字母组成的密码");
        }
    }

    private void checkNameRules(String name){
        if (!name.matches("^[a-zA-Z0-9]+$")) {
            throw new DataException("请输入由数字和字母组成的昵称");
        }
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "注册")
    public R<PlayerTokenResp> register(@RequestBody @Valid PlayerRegisterReq req) {
        log.info("玩家注册入参:{}", JSONUtil.toJsonStr(req));
        String verificationCode = ehcacheService.verificationCache().get(HttpTools.getIp());
        if (verificationCode == null || !verificationCode.equals(req.getVerificationCode())){
            throw new DataException("验证码有误");
        }

        if (StringUtils.isBlank(req.getPhone()) && StringUtils.isBlank(req.getEmail())) {
            throw new DataException("手机号与邮箱不能同时为空");
        }

        checkPasswordRules(req.getPassword());
        checkRegisterName(req.getName());
        checkRegisterPhone(req.getPhone());
        checkRegisterEmail(req.getEmail());

        String salt = GenerateTools.getUUID();

        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setAccount(req.getAccount());
        playerInfo.setName(req.getName());
        playerInfo.setPassword(CodeTools.md5AndSalt(req.getPassword(), salt));
        playerInfo.setSalt(salt);
        playerInfo.setPhone(req.getPhone());
        playerInfo.setCreateName("玩家");
        playerInfo.setCreateTime(LocalDateTime.now());
        playerInfo.setBirth(req.getBirth());
        playerInfo.setGender(req.getGender());
        playerInfoService.save(playerInfo);

        PlayerTokenResp playerTokenResp = createLoginToken(playerInfo);
        return R.ok(playerTokenResp);
    }


    //创建登录token
    private PlayerTokenResp createLoginToken(PlayerInfo playerInfo){
        String tokenId = GenerateTools.createTokenId();
        PlayerTokenResp playerTokenResp = BeanUtil.toBean(playerInfo, PlayerTokenResp.class);
        playerTokenResp.setTokenId(tokenId);
        playerTokenResp.setLoginTime(LocalDateTime.now());

        ehcacheService.playerTokenCache().put(tokenId, playerTokenResp);
        return playerTokenResp;
    }


    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public R<PlayerTokenResp> login(@RequestBody @Valid PlayerLoginReq req) {
        log.info("玩家登录入参:{}", JSONUtil.toJsonStr(req));
        String verificationCode = ehcacheService.verificationCache().get(HttpTools.getIp());
        if (verificationCode == null || !verificationCode.equals(req.getVerificationCode())){
            throw new DataException("验证码有误");
        }

        checkPasswordRules(req.getPassword());

        if (StringUtils.isAnyBlank(req.getPhone(), req.getName(), req.getEmail(), req.getAccount())) {
            throw new DataException("登录方式不能为空");
        }
        if (StringUtils.isNotBlank(req.getEmail()) && req.getEmail().length() > 50) {
            throw new DataException("邮箱长度输入过长");
        }
        if (StringUtils.isNotBlank(req.getPhone()) && req.getPhone().length() > 20) {
            throw new DataException("手机号输入过长");
        }
        if (StringUtils.isNotBlank(req.getName()) && req.getName().length() > 20) {
            throw new DataException("昵称输入过长");
        }
        if (StringUtils.isNotBlank(req.getAccount()) && req.getAccount().length() > 20) {
            throw new DataException("账号输入过长");
        }

        //根据登录方式查询账号
        PlayerInfo playerInfo = playerInfoService.findByLogin(req.getAccount(), req.getName(), req.getPhone(), req.getEmail());
        if (playerInfo == null) {
            throw new AccountOrPasswordException();
        }
        if (!playerInfo.getPassword().equals(CodeTools.md5AndSalt(req.getPassword(), playerInfo.getSalt()))) {
            throw new AccountOrPasswordException();
        }

        PlayerTokenResp playerTokenResp = createLoginToken(playerInfo);
        return R.ok(playerTokenResp);
    }

}
