package com.baiyi.caesar.datasource.aliyun.provider;

import com.aliyuncs.ecs.model.v20140526.DescribeVSwitchesResponse;
import com.baiyi.caesar.common.annotation.SingleTask;
import com.baiyi.caesar.common.datasource.AliyunDsInstanceConfig;
import com.baiyi.caesar.common.datasource.config.DsAliyunConfig;
import com.baiyi.caesar.common.type.DsAssetTypeEnum;
import com.baiyi.caesar.common.type.DsTypeEnum;
import com.baiyi.caesar.datasource.aliyun.convert.VSwitchAssetConvert;
import com.baiyi.caesar.datasource.aliyun.ecs.handler.AliyunVpcHandler;
import com.baiyi.caesar.datasource.model.DsInstanceContext;
import com.baiyi.caesar.datasource.provider.asset.BaseAssetProvider;
import com.baiyi.caesar.datasource.builder.AssetContainer;
import com.baiyi.caesar.datasource.factory.AssetProviderFactory;
import com.baiyi.caesar.datasource.util.AssetUtil;
import com.baiyi.caesar.domain.generator.caesar.DatasourceConfig;
import com.baiyi.caesar.domain.generator.caesar.DatasourceInstance;
import com.baiyi.caesar.domain.generator.caesar.DatasourceInstanceAsset;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Author <a href="mailto:xiuyuan@xinc818.group">修远</a>
 * @Date 2021/6/23 2:00 下午
 * @Since 1.0
 */

@Component
public class AliyunVSwitchProvider extends BaseAssetProvider<DescribeVSwitchesResponse.VSwitch> {

    @Resource
    private AliyunVpcHandler aliyunVpcHandler;

    @Resource
    private AliyunVSwitchProvider aliyunVSwitchProvider;

    @Resource
    private VSwitchAssetConvert vSwitchAssetConvert;

    @Override
    @SingleTask(name = "PullAliyunVSwitch")
    public void pullAsset(int dsInstanceId) {
        doPull(dsInstanceId);
    }

    private DsAliyunConfig.Aliyun buildConfig(DatasourceConfig dsConfig) {
        return dsFactory.build(dsConfig, AliyunDsInstanceConfig.class).getAliyun();
    }

    @Override
    protected AssetContainer toAssetContainer(DatasourceInstance dsInstance, DescribeVSwitchesResponse.VSwitch entry) {
        return vSwitchAssetConvert.toAssetContainer(dsInstance, entry);
    }

    @Override
    protected boolean equals(DatasourceInstanceAsset asset, DatasourceInstanceAsset preAsset) {
        if (!AssetUtil.equals(preAsset.getDescription(), asset.getDescription()))
            return false;
        return true;
    }

    @Override
    protected List<DescribeVSwitchesResponse.VSwitch> listEntries(DsInstanceContext dsInstanceContext) {
        DsAliyunConfig.Aliyun aliyun = buildConfig(dsInstanceContext.getDsConfig());
        if (CollectionUtils.isEmpty(aliyun.getRegionIds()))
            return Collections.emptyList();
        List<DescribeVSwitchesResponse.VSwitch> vSwitchList = Lists.newArrayList();
        aliyun.getRegionIds().forEach(regionId -> vSwitchList.addAll(aliyunVpcHandler.listVSwitches(regionId, aliyun)));
        return vSwitchList;
    }

    @Override
    public String getInstanceType() {
        return DsTypeEnum.ALIYUN.name();
    }

    @Override
    public String getAssetType() {
        return DsAssetTypeEnum.V_SWITCH.getType();
    }

    @Override
    public void afterPropertiesSet() {
        AssetProviderFactory.register(aliyunVSwitchProvider);
    }

}