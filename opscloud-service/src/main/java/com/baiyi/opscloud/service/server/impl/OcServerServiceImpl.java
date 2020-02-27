package com.baiyi.opscloud.service.server.impl;

import com.baiyi.opscloud.domain.DataTable;
import com.baiyi.opscloud.domain.generator.OcServer;
import com.baiyi.opscloud.domain.param.server.ServerParam;
import com.baiyi.opscloud.mapper.OcServerMapper;
import com.baiyi.opscloud.service.server.OcServerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author baiyi
 * @Date 2020/1/10 1:25 下午
 * @Version 1.0
 */
@Service
public class OcServerServiceImpl implements OcServerService {

    @Resource
    private OcServerMapper ocServerMapper;

    @Override
    public OcServer queryOcServerByPrivateIp(String privateIp) {
        Example example = new Example(OcServer.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("privateIp", privateIp);
        return ocServerMapper.selectOneByExample(example);
    }

    @Override
    public int countByServerGroupId(int id) {
        Example example = new Example(OcServer.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serverGroupId", id);
        return ocServerMapper.selectCountByExample(example);
    }

    @Override
    public int countByEnvType(int envType) {
        Example example = new Example(OcServer.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("envType", envType);
        return ocServerMapper.selectCountByExample(example);
    }

    @Override
    public DataTable<OcServer> queryOcServerByParam(ServerParam.PageQuery pageQuery) {
        Page page = PageHelper.startPage(pageQuery.getPage(), pageQuery.getLength().intValue());
        List<OcServer> ocServerList = ocServerMapper.queryOcServerByParam(pageQuery);
        return new DataTable<>(ocServerList, page.getTotal());
    }

    @Override
    public void addOcServer(OcServer ocServer) {
       ocServerMapper.insert(ocServer);
    }

    @Override
    public void updateOcServer(OcServer ocServer) {
        ocServerMapper.updateByPrimaryKey(ocServer);
    }

    @Override
    public void deleteOcServerById(int id) {
        ocServerMapper.deleteByPrimaryKey(id);
    }


}