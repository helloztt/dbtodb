package com.huotu.hotsupplier.type.service.mssql.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmBrand;
import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mssql.HbmTypeBrand;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mssql.HbmBrandRepository;
import com.huotu.hotsupplier.type.repository.mssql.HbmTypeBrandRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmTypeBrandService;
import com.huotu.hotsupplier.type.util.Constant;
import com.huotu.hotsupplier.type.worker.StartRunner;
import com.huotu.hotsupplier.type.worker.Starter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.SimpleTimeZone;

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
        long start = System.currentTimeMillis();
        if (brandList != null && brandList.size() > 0) {
            List<HbmTypeBrand> saveTypeBrand = new ArrayList<>();
            brandList.forEach(b -> {
                //从缓存中读取brandId
                Integer brandId = StartRunner.brandMap.get(String.valueOf(b.getId()));
                //如果缓存读取失败，则从数据库中读取
                if (brandId == null) {
                    brandId = brandRepository.findByStandardBrandId(String.valueOf(b.getId())).getBrandId();
                }
                HbmTypeBrand old = typeBrandRepository.findByTypeIdAndBrandId(type.getTypeId(), brandId);
                if (old == null) {
                    HbmTypeBrand typeBrand = new HbmTypeBrand();
                    typeBrand.setTypeId(type.getTypeId());
                    typeBrand.setBrandId(brandId);
                    saveTypeBrand.add(typeBrand);
                }
                if (saveTypeBrand.size() > 0 && saveTypeBrand.size() % Constant.PAGESIZE == 0) {
                    typeBrandRepository.save(saveTypeBrand);
                    saveTypeBrand.clear();
                }
            });
            if (saveTypeBrand.size() > 0) {
                typeBrandRepository.save(saveTypeBrand);
            }
        }
        long end = System.currentTimeMillis();
        log.info("save type brand " + brandList.size() + " last " + (end - start) + "ms");
    }

    @Override
    public long getTypeBrandCount() {
        return typeBrandRepository.countByCustomerId(-1);
    }
}
