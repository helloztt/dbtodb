package com.huotu.hotsupplier.type.service.mssql.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mysql.Category;
import com.huotu.hotsupplier.type.repository.mssql.HbmGoodsTypeRepository;
import com.huotu.hotsupplier.type.repository.mssql.HbmSpecValuesRepository;
import com.huotu.hotsupplier.type.repository.mssql.HbmSpecificationRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by admin on 2016/1/21.
 */
@Service
public class HbmGoodsTypeServiceImpl implements HbmGoodsTypeService {
    @Autowired
    private HbmGoodsTypeRepository typeRepository;
    @Autowired
    private HbmSpecificationRepository specificationRepository;
    @Autowired
    private HbmSpecValuesRepository specValuesRepository;

    //保存商品类目，及中间表
    public HbmGoodsType saveType(Category category, String parentPath) {
        HbmGoodsType type = new HbmGoodsType();
        type.setName(category.getName());
        type.setStandardTypeId(String.valueOf(category.getCid()));
        type.setParent(category.isParent());
        //如果父类目ID不为空
        if (category.getParentCid() == null) {
            type.setParentStandardTypeId("0");
        } else {
            //找到父类目，拼接path
            type.setParentStandardTypeId(String.valueOf(category.getParentCid()));
        }
        type.setPath(parentPath + type.getStandardTypeId() + "|");
        if ("normal".equals(category.getStatus())) {
            type.setDisabled(false);
        } else {
            type.setDisabled(true);
        }
        type.setCustomerId(-1);
        type.setTOrder(0);
        type.setSchemaId(category.getStatus());
        type.setLastmodify(new Date());
        type = typeRepository.save(type);
        return type;
    }

    @Override
    public HbmGoodsType findByStandardTypeId(String standardTypeId) {
        return typeRepository.findByStandardTypeId(standardTypeId);
    }

    @Override
    public long getTypeCount() {
        return typeRepository.count();
    }
}
