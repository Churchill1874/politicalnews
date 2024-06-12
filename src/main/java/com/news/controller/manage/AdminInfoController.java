package com.news.controller.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.news.common.exception.AccountOrPasswordException;
import com.news.common.exception.DataException;
import com.news.common.tools.CodeTools;
import com.news.common.tools.GenerateTools;
import com.news.common.tools.HttpTools;
import com.news.entity.AdminInfo;
import com.news.pojo.req.PageBase;
import com.news.pojo.req.admin.AdminLoginReq;
import com.news.pojo.req.admin.AdminPageReq;
import com.news.pojo.resp.admin.AdminTokenResp;
import com.news.service.AdminInfoService;
import com.news.service.EhcacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "管理员")
@RequestMapping("/manage/adminInfo")
public class AdminInfoController {

    @Autowired
    private EhcacheService ehcacheService;
    @Autowired
    private AdminInfoService adminInfoService;

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public R<AdminTokenResp> login(@RequestBody @Valid AdminLoginReq req) {
        log.info("登录入参:{}", JSONUtil.toJsonStr(req));

        String verificationCode = ehcacheService.verificationCache().get(HttpTools.getIp());
        if (verificationCode == null || !verificationCode.equals(req.getVerificationCode())){
            throw new DataException("验证码有误");
        }

        AdminInfo adminInfo = adminInfoService.findByAccount(req.getAccount());
        if (adminInfo == null || !adminInfo.getPassword().equals(CodeTools.md5AndSalt(req.getPassword(), adminInfo.getSalt()))) {
            throw new AccountOrPasswordException();
        }

        String tokenId = GenerateTools.createTokenId();
        AdminTokenResp adminTokenResp = BeanUtil.toBean(adminInfo, AdminTokenResp.class);
        adminTokenResp.setTokenId(tokenId);
        ehcacheService.adminTokenCache().put(tokenId, adminTokenResp);

        return R.ok(adminTokenResp);
    }

    @PostMapping("/queryPage")
    @ApiOperation(value = "分页", notes = "分页")
    public R<IPage<AdminInfo>> queryPage(@RequestBody @Valid AdminPageReq req) {
        log.info("分页查询管理员入参:{}", JSONUtil.toJsonStr(req));
        AdminInfo adminInfo = BeanUtil.toBean(req, AdminInfo.class);

        PageBase pageBase = BeanUtil.toBean(req, PageBase.class);

        IPage<AdminInfo> iPage = adminInfoService.queryPage(adminInfo, pageBase);
        return R.ok(iPage);
    }


}
