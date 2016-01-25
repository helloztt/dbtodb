package com.huotu.hotsupplier.type.service.mssql.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmSpecification;
import com.huotu.hotsupplier.type.entity.mysql.Property;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mssql.HbmSpecificationRepository;
import com.huotu.hotsupplier.type.repository.mysql.PropertyValueRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmSpecValuesService;
import com.huotu.hotsupplier.type.service.mssql.HbmSpecificationService;
import com.huotu.hotsupplier.type.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
@Service
public class HbmSpecificationServiceImpl implements HbmSpecificationService {
    @Autowired
    private HbmSpecificationRepository specificationRepository;
    @Autowired
    private PropertyValueRepository propertyValueRepository;
    @Autowired
    private HbmSpecValuesService specValuesService;



    public void saveSpecList(List<Property> propertyList) {
        if (propertyList != null && propertyList.size() > 0) {
            propertyList.forEach(p -> {
                HbmSpecification spec = new HbmSpecification();
                spec.setSpecName(p.getName());
                spec.setOrder(p.getSortOrder());
                if ("normal".equals(p.getStatus())) {
                    spec.setDisabled(false);
                } else {
                    spec.setDisabled(true);
                }
                spec.setStandardSpecId(String.valueOf(p.getId()));
                spec.setLastmodify(new Date());
                spec = specificationRepository.save(spec);
                //保存规格值
                int page = 0;
                Page<PropertyValue> propertyValueFirstPage = propertyValueRepository.findByProperty_IdAndProperty_SaleProperty(p.getId(), true,
                        new PageRequest(page, Constant.PAGESIZE));
                specValuesService.saveSpecValues(propertyValueFirstPage.getContent(), spec.getSpecId());
                int total = propertyValueFirstPage.getTotalPages();
                for (page = 1; page < total; page ++) {
                    Page<PropertyValue> propertyValuePage = propertyValueRepository.findByProperty_IdAndProperty_SaleProperty(p.getId(), true,
                            new PageRequest(page, Constant.PAGESIZE));
                    specValuesService.saveSpecValues(propertyValuePage.getContent(), spec.getSpecId());
                }
            });
        }
    }
}
