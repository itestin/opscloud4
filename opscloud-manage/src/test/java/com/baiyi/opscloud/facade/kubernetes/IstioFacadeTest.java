package com.baiyi.opscloud.facade.kubernetes;

import com.baiyi.opscloud.BaseUnit;
import com.baiyi.opscloud.datasource.kubernetes.exception.KubernetesException;
import com.baiyi.opscloud.domain.param.kubernetes.IstioParam;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

/**
 * @Author baiyi
 * @Date 2023/11/27 11:23
 * @Version 1.0
 */
class IstioFacadeTest extends BaseUnit {

    @Resource
    private IstioFacade istioFacade;

    private static final String YAML = """
            ---
            apiVersion: "networking.istio.io/v1alpha3"
            kind: "VirtualService"
            metadata:
              name: "qa-data-center-daily"
              namespace: "daily"
            spec:
              hosts:
              - "qa-data-center"
              http:
              - route:
                - destination:
                    host: "qa-data-center"
                    subset: "stable"
            """;


    @Test
    void test() {
        IstioParam.UpdateResource updateResource = IstioParam.UpdateResource.builder()
                .resourceYaml(YAML)
                .instanceId(38)
                .build();
        try {
            istioFacade.updateIstioVirtualService(updateResource);
        } catch (KubernetesException e) {
            e.printStackTrace();
        }
    }



    private static final String YAML2 = """
---
apiVersion: "networking.istio.io/v1alpha3"
kind: "DestinationRule"
metadata:
  name: "pay-route-daily"
  namespace: "daily"
spec:
  host: "pay-route"
  subsets:
  - labels:
      xdc: "stable"
    name: "stable"
  - labels:
      xdc: "dc198"
    name: "dc198"
""";

    @Test
    void test2() {
        IstioParam.CreateResource createResource = IstioParam.CreateResource.builder()
                .resourceYaml(YAML2)
                .instanceId(24)
                .build();
        try {
            istioFacade.createIstioDestinationRule(createResource);
        } catch (KubernetesException e) {
            e.printStackTrace();
        }
    }

}