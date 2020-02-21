package com.baiyi.opscloud.service.server;

import com.baiyi.opscloud.domain.DataTable;
import com.baiyi.opscloud.domain.generator.OcEnv;
import com.baiyi.opscloud.domain.param.env.EnvParam;

/**
 * @Author baiyi
 * @Date 2020/1/10 2:16 下午
 * @Version 1.0
 */
public interface OcEnvService {

    OcEnv queryOcEnvById(Integer id);

    OcEnv queryOcEnvByName(String name);

    DataTable<OcEnv> queryOcEnvByParam(EnvParam.PageQuery pageQuery);

    void addOcEnv(OcEnv ocEnv);

    void updateOcEnv(OcEnv ocEnv);

    void deleteOcEnvById(int id);
}
