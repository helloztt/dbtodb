package com.huotu.hotsupplier.type.service.mssql;

import com.huotu.hotsupplier.type.entity.mssql.HbmSpecification;
import com.huotu.hotsupplier.type.entity.mysql.Property;

import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
public interface HbmSpecificationService {
    void getSpecList();

    void saveSpecList(List<Property> propertyList);

    HbmSpecification saveSpec(Property property);

    long getSpecCount();
}
