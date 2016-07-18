package com.huotu.hotsupplier.type.service.mysql;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;

/**
 * Created by admin on 2016/1/22.
 */
public interface CategoryService {
    void saveType();

    void saveSome(Long propertyId);

    void saveCategory(Long parentCid,String parentPath);

    void saveTypeSpec(Long categoryId,HbmGoodsType type);

    void saveBrand(Long categoryId, HbmGoodsType type);
}
