package com.baiyi.opscloud.mapper;

import com.baiyi.opscloud.domain.generator.OcUser;
import com.baiyi.opscloud.domain.param.user.UserParam;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OcUserMapper extends Mapper<OcUser> {

    OcUser queryByUsername(@Param("username") String username);

    List<OcUser> queryOcUserByParam(UserParam.PageQuery pageQuery);

}