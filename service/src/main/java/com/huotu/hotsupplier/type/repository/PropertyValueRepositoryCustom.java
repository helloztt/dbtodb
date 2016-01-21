package com.huotu.hotsupplier.type.repository;

import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;

import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
public interface PropertyValueRepositoryCustom {
    List<PropertyValue> findBrandList(int start,int limit);
}
