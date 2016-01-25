package com.huotu.hotsupplier.type.service.mssql;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;

/**
 * Created by admin on 2016/1/22.
 */
public interface HbmGoodsTypeSpecService {
    void saveTypeSpec(HbmGoodsType type,Long propertyId,Long propertyValueId);
}
