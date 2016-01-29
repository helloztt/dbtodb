package com.huotu.hotsupplier.type.service.mssql.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmSpecValues;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mssql.HbmSpecValuesRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmSpecValuesService;
import com.huotu.hotsupplier.type.util.Constant;
import com.huotu.hotsupplier.type.worker.Starter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
@Service
public class HbmSpecValuesServiceImpl implements HbmSpecValuesService {
    @Autowired
    private HbmSpecValuesRepository specValuesRepository;

    @Override
    public void getSpecValueList() {
        int page = 0;
        Page<HbmSpecValues> specValueFirstPage = specValuesRepository.findBycustomerId(-1, new PageRequest(page, Constant.PAGESIZE));
        int totalPage = specValueFirstPage.getTotalPages();
        if (totalPage > 0) {
            specValueFirstPage.getContent().forEach(p -> {
                if (!Starter.specValueMap.containsKey(p.getStandardSpecValueId())) {
                    Starter.specValueMap.put(p.getStandardSpecValueId(), p.getId());
                }
            });
            for (page = 1; page < totalPage; page++) {
                Page<HbmSpecValues> specValuesPage = specValuesRepository.findBycustomerId(-1, new PageRequest(page, Constant.PAGESIZE));
                specValuesPage.getContent().forEach(p -> {
                    if (!Starter.specValueMap.containsKey(p.getStandardSpecValueId())) {
                        Starter.specValueMap.put(p.getStandardSpecValueId(), p.getId());
                    }
                });
            }
        }

    }

    @Override
    public void saveSpecValues(List<PropertyValue> propertyValueList, int specId) {
        if (propertyValueList != null && propertyValueList.size() > 0) {
            List<HbmSpecValues> saveSpecValues = new ArrayList<>();
            propertyValueList.forEach(p -> {
//                HbmSpecValues old = specValuesRepository.findByStandardSpecValueId(String.valueOf(p.getId()));
                if (!Starter.specValueMap.containsKey(String.valueOf(p.getId()))) {
                    HbmSpecValues hbmSpecValues = new HbmSpecValues();
                    hbmSpecValues.setSpecId(specId);
                    hbmSpecValues.setValue(p.getName());
                    hbmSpecValues.setAlias(p.getNameAlias());
                    hbmSpecValues.setOrder(p.getSortOrder());
                    hbmSpecValues.setStandardSpecValueId(String.valueOf(p.getId()));
                    saveSpecValues.add(hbmSpecValues);
                }
            });
            specValuesRepository.save(saveSpecValues).forEach(p -> {
                if (!Starter.specValueMap.containsKey(p.getStandardSpecValueId())) {
                    Starter.specValueMap.put(p.getStandardSpecValueId(), p.getId());
                }
            });
        }
    }
}
