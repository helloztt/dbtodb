package com.huotu.hotsupplier.type.service.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmBrand;
import com.huotu.hotsupplier.type.entity.mysql.Property;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.HbmBrandRepository;
import com.huotu.hotsupplier.type.repository.PropertyValueRepository;
import com.huotu.hotsupplier.type.service.HbmBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
@Service
public class HbmBrandServiceImpl implements HbmBrandService {
    @Autowired
    private HbmBrandRepository brandRepository;

    //保存品牌列表
    public void saveBrandList(List<PropertyValue> brandList) {
        if (brandList != null && brandList.size() > 0) {
            brandList.forEach(b -> {
                HbmBrand brand = new HbmBrand();
                brand.setBrandName(b.getName());
                brand.setCustomerId(-1);
                brand.setStandardBrandId(String.valueOf(b.getId()));
                brand.setOrderNum(b.getSortOrder());
                if ("normal".equals(b.getStatus())) {
                    brand.setDisabled(false);
                } else {
                    brand.setDisabled(true);
                }
                brandRepository.save(brand);
            });
        }
    }
}
