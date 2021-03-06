package com.huotu.hotsupplier.type.service.mssql;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mssql.HbmSpecification;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;

import java.util.List;

/**
 * Created by admin on 2016/1/22.
 */
public interface HbmGoodsTypeSpecService {
    void saveTypeSpec(HbmGoodsType type,Integer specId,List<PropertyValue> propertyValueList);

    long getTypeSpecCount();
}
