package com.news.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.news.entity.base.BaseInfo;
import lombok.Data;

@Data
public class CreatorBaseInfo extends BaseInfo {
    private static final long serialVersionUID = 161115347113930564L;
    //创建人
    @TableField("create_name")
    private String createName;
    //修改人
    @TableField("update_name")
    private String updateName;

}
