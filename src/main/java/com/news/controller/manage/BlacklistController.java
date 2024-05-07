package com.news.controller.manage;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "黑名单")
@RequestMapping("/manage/blacklist")
public class BlacklistController {
/*

    @Autowired
    private BlacklistService blacklistService;

    @AdminLoginCheck
    @PostMapping("/add")
    @ApiOperation(value = "添加黑名单", notes = "添加黑名单")
    public R add(@RequestBody @Valid BlacklistAdd req) {
        if (StringUtils.isBlank(req.getIp()) && StringUtils.isBlank(req.getPhoneNumber())) {
            return R.failed("ip和手机号不能同时为空");
        }

        //校验ip是否已经添加过
        if (StringUtils.isNotBlank(req.getIp())) {
            List<Blacklist> list = blacklistService.findByIp(req.getIp());
            if (CollectionUtils.isNotEmpty(list)) {
                return R.failed("ip已经添加过");
            }
        }

        Blacklist blacklist = new Blacklist();
        BeanUtils.copyProperties(req, blacklist);

        return R.ok(blacklistService.insert(blacklist));
    }


    @SuperAdminLoginCheck
    @PostMapping("/del")
    @ApiOperation(value = "删除黑名单", notes = "删除黑名单")
    public R del(@RequestBody @Valid IdList req) {
        return R.ok(blacklistService.del(req.getIdList()));
    }


    @AdminLoginCheck
    @PostMapping("/page")
    @ApiOperation(value = "分页黑名单", notes = "分页黑名单")
    public R<IPage<Blacklist>> page(@RequestBody BlacklistPage req) {
        return R.ok(blacklistService.page(req.getPageNum(), req.getPageSize(), req.getIp(), req.getPhoneNumber(), req.getDevice()));
    }
*/


}
