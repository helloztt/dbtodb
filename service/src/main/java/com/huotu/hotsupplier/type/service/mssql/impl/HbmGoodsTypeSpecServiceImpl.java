package com.huotu.hotsupplier.type.service.mssql.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsTypeSpec;
import com.huotu.hotsupplier.type.entity.mssql.HbmSpecValues;
import com.huotu.hotsupplier.type.entity.mssql.HbmSpecification;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mssql.HbmGoodsTypeRepository;
import com.huotu.hotsupplier.type.repository.mssql.HbmGoodsTypeSpecRepository;
import com.huotu.hotsupplier.type.repository.mssql.HbmSpecValuesRepository;
import com.huotu.hotsupplier.type.repository.mssql.HbmSpecificationRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmGoodsTypeSpecService;
import com.huotu.hotsupplier.type.worker.Starter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/1/22.
 */
@Service
public class HbmGoodsTypeSpecServiceImpl implements HbmGoodsTypeSpecService {
    private static final Log log = LogFactory.getLog(HbmGoodsTypeSpecServiceImpl.class);
    @Autowired
    private HbmGoodsTypeRepository typeRepository;
    @Autowired
    private HbmSpecificationRepository specRepository;
    @Autowired
    private HbmSpecValuesRepository specValuesRepository;
    @Autowired
    private HbmGoodsTypeSpecRepository typeSpecRepository;

    @Override
    public void saveTypeSpec(HbmGoodsType type, Integer specId,List<PropertyValue> propertyValueList) {
        long start = System.currentTimeMillis();
        if(propertyValueList != null && propertyValueList.size() > 0){
            List<HbmGoodsTypeSpec> saveTypeSpec = new ArrayList<>();
            propertyValueList.forEach(propertyValue -> {
                //从缓存中读取规格值ID
                Integer specValueId = Starter.specValueMap.get(String.valueOf(propertyValue.getId()));
                //如果缓存读取失败，则读取数据库
                if(specValueId == null){
                    specValueId = specValuesRepository.findByStandardSpecValueId(String.valueOf(propertyValue.getId())).getId();
                }
                HbmGoodsTypeSpec old = typeSpecRepository.findByTypeIdAndSpecIdAndSpecValueId(type.getTypeId(),specId,specValueId);
                if(old == null){
                    HbmGoodsTypeSpec typeSpec = new HbmGoodsTypeSpec();
                    typeSpec.setTypeId(type.getTypeId());
                    typeSpec.setSpecId(specId);
                    typeSpec.setSpecValueId(specValueId);
                    saveTypeSpec.add(typeSpec);
                }
            });
            typeSpecRepository.save(saveTypeSpec);
        }
        long end = System.currentTimeMillis();
        log.info("save type spec value " + propertyValueList.size() + " ,last " + (end-start) + "ms");
    }

    @Override
    public long getTypeSpecCount() {
        return typeSpecRepository.count();
    }
}
