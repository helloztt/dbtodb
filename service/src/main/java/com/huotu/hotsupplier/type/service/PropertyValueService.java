package com.huotu.hotsupplier.type.service;

import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import org.springframework.data.domain.Page;

/**
 * Created by admin on 2016/1/22.
 */
public interface PropertyValueService {

    void saveBrand();

    Page<PropertyValue> getBrandPages(int start);
}
