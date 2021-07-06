package com.baiyi.opscloud.service.datasource;

import com.baiyi.opscloud.domain.DataTable;
import com.baiyi.opscloud.domain.generator.opscloud.DatasourceInstanceAsset;
import com.baiyi.opscloud.domain.param.datasource.DsAssetParam;

import java.util.List;

/**
 * @Author baiyi
 * @Date 2021/6/17 1:40 下午
 * @Version 1.0
 */
public interface DsInstanceAssetService {

    void deleteById(Integer id);

    DatasourceInstanceAsset getById(Integer id);

    void add(DatasourceInstanceAsset asset);

    void update(DatasourceInstanceAsset asset);

    List<DatasourceInstanceAsset> listByInstanceAssetType(String instanceUuid, String assetType);

    DatasourceInstanceAsset getByUniqueKey(DatasourceInstanceAsset asset);

    DataTable<DatasourceInstanceAsset> queryPageByParam(DsAssetParam.AssetPageQuery pageQuery);

    List<String> queryInstanceAssetTypes(String instanceUuid);

    List<DatasourceInstanceAsset> queryAssetByAssetParam(DatasourceInstanceAsset asset);

    int countByInstanceAssetType(String instanceUuid, String assetType);

    List<DatasourceInstanceAsset> listByParentId(Integer parentId);

}
