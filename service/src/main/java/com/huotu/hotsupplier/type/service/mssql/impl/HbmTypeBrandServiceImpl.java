package com.huotu.hotsupplier.type.service.mssql.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmBrand;
import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mssql.HbmTypeBrand;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mssql.HbmBrandRepository;
import com.huotu.hotsupplier.type.repository.mssql.HbmTypeBrandRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmTypeBrandService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

/**
 * Created by admin on 2016/1/22.
 */
@Service
public class HbmTypeBrandServiceImpl implements HbmTypeBrandService {
    private static final Log log = LogFactory.getLog(HbmGoodsTypeSpecServiceImpl.class);
    @Autowired
    private HbmTypeBrandRepository typeBrandRepository;
    @Autowired
    private HbmBrandRepository brandRepository;
    @Override
    public void saveTypeBrand(List<PropertyValue> brandList, HbmGoodsType type) {
        if(brandList != null && brandList.size() > 0){
            brandList.forEach(b -> {
                HbmBrand brand = brandRepository.findByStandardBrandId(String.valueOf(b.getId()));
                if (brand == null) {
                    log.error("save type brand error -- brand null:standardBrandId" + b.getId() + "standardTypeId:" + type.getStandardTypeId());
                    return;
                }
                HbmTypeBrand typeBrand = new HbmTypeBrand();
                typeBrand.setTypeId(type.getTypeId());
                typeBrand.setBrandId(brand.getBrandId());
                typeBrandRepository.save(typeBrand);
            });
        }
    }
}
