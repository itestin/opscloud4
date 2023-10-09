package com.baiyi.opscloud.facade.template.factory.impl;

import com.baiyi.opscloud.common.constants.enums.DsTypeEnum;
import com.baiyi.opscloud.common.datasource.KubernetesConfig;
import com.baiyi.opscloud.datasource.kubernetes.driver.KubernetesCustomResourceDriver;
import com.baiyi.opscloud.domain.constants.DsAssetTypeConstants;
import com.baiyi.opscloud.domain.generator.opscloud.BusinessTemplate;
import com.baiyi.opscloud.domain.generator.opscloud.DatasourceConfig;
import com.baiyi.opscloud.facade.template.factory.base.AbstractTemplateProvider;
import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinition;
import org.springframework.stereotype.Component;

import static com.baiyi.opscloud.common.constants.TemplateKeyConstants.CUSTOM_RESOURCE;


/**
 * @Author baiyi
 * @Date 2023/9/27 11:22
 * @Version 1.0
 */
@Component
public class KubernetesCustomResourceTemplateProvider extends AbstractTemplateProvider<CustomResourceDefinition> {

    @Override
    protected CustomResourceDefinition produce(BusinessTemplate bizTemplate, String content) {
        DatasourceConfig dsConfig = dsConfigManager.getConfigByInstanceUuid(bizTemplate.getInstanceUuid());
        KubernetesConfig.Kubernetes config = dsConfigManager.build(dsConfig, KubernetesConfig.class).getKubernetes();
        return KubernetesCustomResourceDriver.create(config, content);
    }

    @Override
    public String getAssetType() {
        return DsAssetTypeConstants.KUBERNETES_CUSTOM_RESOURCE.name();
    }

    @Override
    public String getInstanceType() {
        return DsTypeEnum.KUBERNETES.getName();
    }

    @Override
    protected boolean hasApplicationResources() {
        return true;
    }

    @Override
    public String getTemplateKey() {
        return CUSTOM_RESOURCE.name();
    }

}
