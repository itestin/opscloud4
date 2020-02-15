package com.baiyi.opscloud.mapper;

import com.baiyi.opscloud.domain.generator.OcAuthGroup;
import com.baiyi.opscloud.domain.param.auth.GroupParam;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OcAuthGroupMapper extends Mapper<OcAuthGroup> {

    List<OcAuthGroup> queryOcAuthGroupByParam(GroupParam.PageQuery pageQuery);
}