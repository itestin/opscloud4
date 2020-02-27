package com.baiyi.opscloud.common.util;

import com.baiyi.opscloud.domain.bo.OcServerBO;
import com.baiyi.opscloud.domain.generator.OcEnv;
import com.baiyi.opscloud.domain.generator.OcServer;
import com.google.common.base.Joiner;

/**
 * @Author baiyi
 * @Date 2020/1/10 1:32 下午
 * @Version 1.0
 */
public class ServerUtils {

    /**
     * 带SN
     * @param ocServerBO
     * @return server-prod-1
     */
    public static String toServerName(OcServerBO ocServerBO){
        OcServer ocServer = ocServerBO.getOcServer();
        OcEnv ocEnv = ocServerBO.getOcEnv();
        return Joiner.on("-").join(ocServer.getName() ,ocEnv.getEnvName(), ocServer.getSerialNumber());
    }

    /**
     * 不带SN
     * @param ocServerBO
     * @return server-prod
     */
    public static String toHostName(OcServerBO ocServerBO){
        OcServer ocServer = ocServerBO.getOcServer();
        OcEnv ocEnv = ocServerBO.getOcEnv();
        return Joiner.on("-").join(ocServer.getName() ,ocEnv.getEnvName());
    }

}