package com.huotu.hotsupplier.type.service.mssql.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmSpecification;
import com.huotu.hotsupplier.type.entity.mysql.Property;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mssql.HbmSpecificationRepository;
import com.huotu.hotsupplier.type.repository.mysql.PropertyValueRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmSpecValuesService;
import com.huotu.hotsupplier.type.service.mssql.HbmSpecificationService;
import com.huotu.hotsupplier.type.util.Constant;
import com.huotu.hotsupplier.type.worker.Starter;
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


    @Override
    public void getSpecList() {
        int page = 0 ;
        Page<HbmSpecification> specFirstPage = specificationRepository.findByCustomerId(-1,new PageRequest(page,Constant.READPAGESIZE));
        int totalPage = specFirstPage.getTotalPages();
        if(totalPage > 0){
            specFirstPage.getContent().forEach(p->{
                if(!Starter.specMap.containsKey(p.getStandardSpecId())){
                    Starter.specMap.put(p.getStandardSpecId(),p.getSpecId());
                }
            });
            for(page = 1;page<totalPage ; page ++){
                Page<HbmSpecification> specPage = specificationRepository.findByCustomerId(-1,new PageRequest(page,Constant.READPAGESIZE));
                specPage.getContent().forEach(p->{
                    if(!Starter.specMap.containsKey(p.getStandardSpecId())){
                        Starter.specMap.put(p.getStandardSpecId(),p.getSpecId());
                    }
                });
            }
        }
    }

    public void saveSpecList(List<Property> propertyList) {
        if (propertyList != null && propertyList.size() > 0) {
            propertyList.forEach(p -> {
//                HbmSpecification spec = specificationRepository.findByStandardSpecId(String.valueOf(p.getId()));
                Integer specId;
                if(!Starter.specMap.containsKey(String.valueOf(p.getId()))){
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
                    if(!Starter.specMap.containsKey(spec.getStandardSpecId())){
                        Starter.specMap.put(spec.getStandardSpecId(),spec.getSpecId());
                    }
                    specId = spec.getSpecId();
                }else{
                    specId = Starter.specMap.get(String.valueOf(p.getId()));
                }
                //保存规格值
                int page = 0;
                Page<PropertyValue> propertyValueFirstPage = propertyValueRepository.findByProperty_IdAndProperty_SaleProperty(p.getId(), true,
                        new PageRequest(page, Constant.READPAGESIZE));
                specValuesService.saveSpecValues(propertyValueFirstPage.getContent(), specId);
                int total = propertyValueFirstPage.getTotalPages();
                for (page = 1; page < total; page ++) {
                    Page<PropertyValue> propertyValuePage = propertyValueRepository.findByProperty_IdAndProperty_SaleProperty(p.getId(), true,
                            new PageRequest(page, Constant.READPAGESIZE));
                    specValuesService.saveSpecValues(propertyValuePage.getContent(), specId);
                }
            });
        }
    }

    @Override
    public HbmSpecification saveSpec(Property p) {
        Integer specId;
        HbmSpecification spec = specificationRepository.findByStandardSpecId(p.getId().toString());
        if(spec == null){
            spec = new HbmSpecification();
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
            if(!Starter.specMap.containsKey(spec.getStandardSpecId())){
                Starter.specMap.put(spec.getStandardSpecId(),spec.getSpecId());
            }
            specId = spec.getSpecId();
        }else{
            specId = Starter.specMap.get(String.valueOf(p.getId()));
        }
        //保存规格值
        int page = 0;
        Page<PropertyValue> propertyValueFirstPage = propertyValueRepository.findByProperty_IdAndProperty_SaleProperty(p.getId(), true,
                new PageRequest(page, Constant.PAGESIZE));
        specValuesService.saveSpecValues(propertyValueFirstPage.getContent(), specId);
        int total = propertyValueFirstPage.getTotalPages();
        for (page = 1; page < total; page ++) {
            Page<PropertyValue> propertyValuePage = propertyValueRepository.findByProperty_IdAndProperty_SaleProperty(p.getId(), true,
                    new PageRequest(page, Constant.PAGESIZE));
            specValuesService.saveSpecValues(propertyValuePage.getContent(), specId);
        }
        return spec;
    }

    @Override
    public long getSpecCount() {
        return specificationRepository.count();
    }
}
