package com.huotu.hotsupplier.type.service.mssql.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmSpecValues;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mssql.HbmSpecValuesRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmSpecValuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
@Service
public class HbmSpecValuesServiceImpl implements HbmSpecValuesService {
    @Autowired
    private HbmSpecValuesRepository specValuesRepository;

    @Override
    public void saveSpecValues(List<PropertyValue> propertyValueList,int specId) {
        if (propertyValueList != null && propertyValueList.size() > 0) {
            propertyValueList.forEach(p->{
                HbmSpecValues hbmSpecValues = new HbmSpecValues();
                hbmSpecValues.setSpecId(specId);
                hbmSpecValues.setValue(p.getName());
                hbmSpecValues.setAlias(p.getNameAlias());
                hbmSpecValues.setOrder(p.getSortOrder());
                hbmSpecValues.setStandardSpecValueId(String.valueOf(p.getId()));
                specValuesRepository.save(hbmSpecValues);
            });
        }
    }
}
