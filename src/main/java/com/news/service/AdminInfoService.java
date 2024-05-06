package com.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.news.entity.AdminInfo;

import java.util.List;

public interface AdminInfoService extends IService<AdminInfo> {

    AdminInfo findByAccount(String account);

    AdminInfo findByAccountAndPassword(String account, String password);

    List<AdminInfo> queryList(AdminInfo adminInfo);

}
