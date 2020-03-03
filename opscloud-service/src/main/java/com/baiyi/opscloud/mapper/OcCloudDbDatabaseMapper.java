package com.baiyi.opscloud.mapper;

import com.baiyi.opscloud.domain.generator.OcCloudDbDatabase;
import com.baiyi.opscloud.domain.param.cloud.CloudDBDatabaseParam;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OcCloudDbDatabaseMapper extends Mapper<OcCloudDbDatabase> {

    List<OcCloudDbDatabase> fuzzyQueryOcCloudDbDatabaseByParam(CloudDBDatabaseParam.PageQuery pageQuery);

    int updateBaseOcCloudDbDatabase(OcCloudDbDatabase ocCloudDbDatabase);
}