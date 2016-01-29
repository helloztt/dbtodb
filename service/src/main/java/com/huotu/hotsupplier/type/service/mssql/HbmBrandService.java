package com.huotu.hotsupplier.type.service.mssql;

import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;

import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
public interface HbmBrandService {

    void saveBrandList(List<PropertyValue> brandList);

    void getBrandList();
}
