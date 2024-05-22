package com.news.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.common.constant.enums.ManageRoleEnum;
import com.news.common.constant.enums.UserStatusEnum;
import com.news.common.tools.CodeTools;
import com.news.common.tools.GenerateTools;
import com.news.entity.AdminInfo;
import com.news.mapper.AdminInfoMapper;
import com.news.pojo.req.PageBase;
import com.news.service.AdminInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AdminInfoServiceImpl extends ServiceImpl<AdminInfoMapper, AdminInfo> implements AdminInfoService {

    @Override
    public AdminInfo findByAccount(String account) {
        QueryWrapper<AdminInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(AdminInfo::getAccount, account);
        return getOne(queryWrapper);
    }

    @Override
    public AdminInfo findByAccountAndPassword(String account, String password) {
        QueryWrapper<AdminInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(AdminInfo::getAccount, account)
                .eq(AdminInfo::getPassword, password);
        return getOne(queryWrapper);
    }

    /**
     * 获取一个admin的wrapper查询条件对象
     *
     * @param adminInfo
     * @return
     */
    public QueryWrapper<AdminInfo> getAdminQueryWrapper(AdminInfo adminInfo, PageBase pageBase) {
        QueryWrapper<AdminInfo> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(adminInfo.getAccount()), AdminInfo::getAccount, adminInfo.getAccount())
                .eq(StringUtils.isNotBlank(adminInfo.getPassword()), AdminInfo::getPassword, adminInfo.getPassword())
                .eq(StringUtils.isNotBlank(adminInfo.getName()), AdminInfo::getName, adminInfo.getName())
                .eq(adminInfo.getRole() != null, AdminInfo::getRole, adminInfo.getRole())
                .eq(adminInfo.getId() != null, AdminInfo::getId, adminInfo.getId())
                .ge(pageBase.getStartTime() != null, AdminInfo::getCreateTime, pageBase.getStartTime())
                .le(pageBase.getEndTime() != null, AdminInfo::getCreateTime, pageBase.getEndTime())
                .orderByDesc(AdminInfo::getCreateTime);

        return queryWrapper;
    }

    @Override
    public List<AdminInfo> queryList(AdminInfo adminInfo) {
        QueryWrapper<AdminInfo> queryWrapper = getAdminQueryWrapper(adminInfo, new PageBase());
        return list(queryWrapper);
    }

    @Override
    public IPage<AdminInfo> queryPage(AdminInfo adminInfo, PageBase pageBase) {
        QueryWrapper<AdminInfo> queryWrapper = getAdminQueryWrapper(adminInfo, pageBase);
        IPage<AdminInfo> iPage = new Page<>(pageBase.getPageNumber(), pageBase.getPageSize());
        return page(iPage, queryWrapper);
    }

    @Override
    public void initSuperAccount() {
        String SUPER_ADMIN_ACCOUNT = "superadmin";//超管管理员账号
        String PASSWORD = "111111a";
        AdminInfo adminInfo = findByAccount(SUPER_ADMIN_ACCOUNT);
        if (adminInfo == null) {
            String salt = GenerateTools.getUUID();
            adminInfo = new AdminInfo();
            adminInfo.setAccount(SUPER_ADMIN_ACCOUNT);
            adminInfo.setName("超级管理员");
            adminInfo.setPassword(CodeTools.md5AndSalt(PASSWORD, salt));
            adminInfo.setSalt(salt);
            adminInfo.setRole(ManageRoleEnum.SUPER_ADMIN);
            adminInfo.setCreateName("系统");
            adminInfo.setCreateTime(LocalDateTime.now());
            adminInfo.setStatus(UserStatusEnum.NORMAL);
            save(adminInfo);
            log.info("初始化创建了超级管理员");
        }
    }

}
