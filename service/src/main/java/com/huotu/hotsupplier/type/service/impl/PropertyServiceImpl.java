package com.huotu.hotsupplier.type.service.impl;

import com.huotu.hotsupplier.type.entity.mysql.Property;
import com.huotu.hotsupplier.type.repository.PropertyRepository;
import com.huotu.hotsupplier.type.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by admin on 2016/1/21.
 */
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public void saveProperty() {

    }
}
