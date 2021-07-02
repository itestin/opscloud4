package com.baiyi.opscloud.zabbix.handler;

import com.baiyi.opscloud.common.datasource.config.DsZabbixConfig;
import com.baiyi.opscloud.zabbix.entry.ZabbixUser;
import com.baiyi.opscloud.zabbix.entry.ZabbixUserGroup;
import com.baiyi.opscloud.zabbix.http.ZabbixRequest;
import com.baiyi.opscloud.zabbix.http.ZabbixRequestBuilder;
import com.baiyi.opscloud.zabbix.mapper.ZabbixMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author <a href="mailto:xiuyuan@xinc818.group">修远</a>
 * @Date 2021/6/28 1:23 下午
 * @Since 1.0
 */

@Component
public class ZabbixUserGroupHandler {

    @Resource
    private ZabbixHandler zabbixHandler;

    private interface Method {
        String QUERY_GROUP = "usergroup.get";
    }

    public List<ZabbixUserGroup> listGroups(DsZabbixConfig.Zabbix zabbix) {
        ZabbixRequest request = ZabbixRequestBuilder.builder()
                .method(Method.QUERY_GROUP)
                .build();
        JsonNode data = zabbixHandler.call(zabbix, request);
        return ZabbixMapper.mapperList(data.get("result"), ZabbixUserGroup.class);
    }

    public List<ZabbixUserGroup> listGroupsByUser(DsZabbixConfig.Zabbix zabbix, ZabbixUser user) {
        ZabbixRequest request = ZabbixRequestBuilder.builder()
                .method(Method.QUERY_GROUP)
                .paramEntry("userids", user.getUserId())
                .build();
        JsonNode data = zabbixHandler.call(zabbix, request);
        return ZabbixMapper.mapperList(data.get("result"), ZabbixUserGroup.class);
    }


    public ZabbixUserGroup getGroupById(DsZabbixConfig.Zabbix zabbix, String groupId) {
        ZabbixRequest request = ZabbixRequestBuilder.builder()
                .method(Method.QUERY_GROUP)
                .paramEntry("usrgrpids", groupId)
                .build();
        JsonNode data = zabbixHandler.call(zabbix, request);
        List<ZabbixUserGroup> groups = ZabbixMapper.mapperList(data.get("result"), ZabbixUserGroup.class);
        if (CollectionUtils.isEmpty(groups))
            throw new RuntimeException("ZabbixUserGroup不存在");
        return groups.get(0);
    }
}
