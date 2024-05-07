package com.news.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.entity.AdminInfo;
import com.news.mapper.AdminInfoMapper;
import com.news.service.AdminInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
     * @param adminInfo
     * @return
     */
    public QueryWrapper<AdminInfo> getAdminQueryWrapper(AdminInfo adminInfo){
        QueryWrapper<AdminInfo> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(adminInfo.getAccount()), AdminInfo::getAccount, adminInfo.getAccount())
                .eq(StringUtils.isNotBlank(adminInfo.getPassword()), AdminInfo::getPassword, adminInfo.getPassword())
                .eq(StringUtils.isNotBlank(adminInfo.getName()), AdminInfo::getName, adminInfo.getName())
                .eq(adminInfo.getRole() != null, AdminInfo::getRole, adminInfo.getRole())
                .eq(adminInfo.getId() != null, AdminInfo::getId, adminInfo.getId())
                .orderByDesc(AdminInfo::getCreateTime);

        return queryWrapper;
    }

    @Override
    public List<AdminInfo> queryList(AdminInfo adminInfo) {
        QueryWrapper<AdminInfo> queryWrapper = getAdminQueryWrapper(adminInfo);
        return list(queryWrapper);
    }

    @Override
    public IPage<AdminInfo> queryPage(AdminInfo adminInfo, Integer pageNumber, Integer pageSize) {
        QueryWrapper<AdminInfo> queryWrapper = getAdminQueryWrapper(adminInfo);
        IPage<AdminInfo> iPage = new Page<>(pageNumber, pageSize);
        return page(iPage, queryWrapper);
    }

}
