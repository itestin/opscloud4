package com.baiyi.opscloud.datasource.kubernetes.provider;

import com.baiyi.opscloud.common.constants.enums.DsTypeEnum;
import com.baiyi.opscloud.common.datasource.KubernetesConfig;
import com.baiyi.opscloud.core.factory.AssetProviderFactory;
import com.baiyi.opscloud.core.model.DsInstanceContext;
import com.baiyi.opscloud.core.provider.asset.BaseAssetProvider;
import com.baiyi.opscloud.core.util.AssetUtil;
import com.baiyi.opscloud.datasource.kubernetes.convert.ServiceAssetConvert;
import com.baiyi.opscloud.datasource.kubernetes.drive.KubernetesNamespaceDrive;
import com.baiyi.opscloud.datasource.kubernetes.drive.KubernetesServiceDrive;
import com.baiyi.opscloud.domain.builder.asset.AssetContainer;
import com.baiyi.opscloud.domain.generator.opscloud.DatasourceConfig;
import com.baiyi.opscloud.domain.generator.opscloud.DatasourceInstance;
import com.baiyi.opscloud.domain.generator.opscloud.DatasourceInstanceAsset;
import com.baiyi.opscloud.domain.types.DsAssetTypeEnum;
import com.google.common.collect.Lists;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author baiyi
 * @Date 2021/12/7 6:02 PM
 * @Version 1.0
 */
@Component
public class KubernetesServiceProvider extends BaseAssetProvider<Service> {

    @Resource
    private KubernetesServiceProvider kubernetesServiceProvider;

    @Override
    public String getInstanceType() {
        return DsTypeEnum.KUBERNETES.name();
    }

    @Override
    public String getAssetType() {
        return DsAssetTypeEnum.KUBERNETES_SERVICE.getType();
    }

    private KubernetesConfig.Kubernetes buildConfig(DatasourceConfig dsConfig) {
        return dsConfigHelper.build(dsConfig, KubernetesConfig.class).getKubernetes();
    }

    @Override
    protected List<Service> listEntities(DsInstanceContext dsInstanceContext) {
        KubernetesConfig.Kubernetes kubernetes = buildConfig(dsInstanceContext.getDsConfig());
        List<Namespace> namespaces = KubernetesNamespaceDrive.listNamespace(buildConfig(dsInstanceContext.getDsConfig()));
        List<Service> entities = Lists.newArrayList();
        namespaces.forEach(e ->
                entities.addAll(KubernetesServiceDrive.listService(kubernetes, e.getMetadata().getName()))
        );
        return entities;
    }

    @Override
   // @SingleTask(name = PULL_KUBERNETES_SERVICE, lockTime = "2m")
    public void pullAsset(int dsInstanceId) {
        doPull(dsInstanceId);
    }

    @Override
    protected boolean equals(DatasourceInstanceAsset asset, DatasourceInstanceAsset preAsset) {
        if (!AssetUtil.equals(preAsset.getAssetKey(), asset.getAssetKey()))
            return false;
        if (!AssetUtil.equals(preAsset.getAssetKey2(), asset.getAssetKey2()))
            return false;
        if (!AssetUtil.equals(preAsset.getName(), asset.getName()))
            return false;
        if (preAsset.getIsActive() != asset.getIsActive())
            return false;
        return true;
    }

    @Override
    protected AssetContainer toAssetContainer(DatasourceInstance dsInstance, Service entity) {
        return ServiceAssetConvert.toAssetContainer(dsInstance, entity);
    }

    @Override
    public void afterPropertiesSet() {
        AssetProviderFactory.register(kubernetesServiceProvider);
    }
}
