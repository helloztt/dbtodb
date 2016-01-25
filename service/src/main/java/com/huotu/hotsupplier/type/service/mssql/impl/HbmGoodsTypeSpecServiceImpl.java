package com.huotu.hotsupplier.type.service.mssql.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsTypeSpec;
import com.huotu.hotsupplier.type.entity.mssql.HbmSpecValues;
import com.huotu.hotsupplier.type.entity.mssql.HbmSpecification;
import com.huotu.hotsupplier.type.repository.mssql.HbmGoodsTypeRepository;
import com.huotu.hotsupplier.type.repository.mssql.HbmGoodsTypeSpecRepository;
import com.huotu.hotsupplier.type.repository.mssql.HbmSpecValuesRepository;
import com.huotu.hotsupplier.type.repository.mssql.HbmSpecificationRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmGoodsTypeSpecService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void saveTypeSpec(HbmGoodsType type, Long propertyId, Long propertyValueId) {
//        HbmGoodsType type = typeRepository.findByStandardTypeId(String.valueOf(categoryId));
//        if(type == null ){
//            log.info("save type spec error: categoryId ---- type null:"+ categoryId + "propertyId:" + propertyId + ",propertyValueId:" + propertyValueId );
//            return;
//        }
        HbmSpecification spec = specRepository.findByStandardSpecId(String.valueOf(propertyId));
        if(spec == null){
            log.info("save type spec error: categoryId ---- spec null:"+ type.getStandardTypeId() + "propertyId:" + propertyId + ",propertyValueId:" + propertyValueId );
            return;
        }
        HbmSpecValues specValue = specValuesRepository.findByStandardSpecValueId(String.valueOf(propertyValueId));
        if(specValue == null){
            log.info("save type spec error: categoryId ---- specValue null:"+ type.getStandardTypeId() + "propertyId:" + propertyId + ",propertyValueId:" + propertyValueId );
            return;
        }
        HbmGoodsTypeSpec typeSpec = new HbmGoodsTypeSpec();
        typeSpec.setTypeId(type.getTypeId());
        typeSpec.setSpecId(spec.getSpecId());
        typeSpec.setSpecValueId(specValue.getId());
        typeSpecRepository.save(typeSpec);

    }
}
