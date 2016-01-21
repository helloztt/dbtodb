package com.huotu.hotsupplier.type.service.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsSpecIndex;
import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mssql.HbmSpecValues;
import com.huotu.hotsupplier.type.entity.mysql.Category;
import com.huotu.hotsupplier.type.repository.HbmGoodsSpecIndexRepository;
import com.huotu.hotsupplier.type.repository.HbmGoodsTypeRepository;
import com.huotu.hotsupplier.type.repository.HbmSpecValuesRepository;
import com.huotu.hotsupplier.type.repository.HbmSpecificationRepository;
import com.huotu.hotsupplier.type.service.HbmGoodsTypeService;
import org.eclipse.persistence.internal.sessions.DirectCollectionChangeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private HbmGoodsSpecIndexRepository goodsSpecIndexRepository;

    //保存商品类目，及中间表
    public HbmGoodsType saveType(Category category) {
        HbmGoodsType type = new HbmGoodsType();
        type.setName(category.getName());
        type.setStandardTypeId(String.valueOf(category.getCid()));
        type.setParent(category.isParent());
        //如果父类目ID不为空
        if(category.getParentCid() == null){
            type.setParentStandardTypeId("0");
            type.setPath("|" + type.getStandardTypeId() + "|");
        }else{
            //找到父类目，拼接path
            type.setParentStandardTypeId(String.valueOf(category.getParentCid()));
            HbmGoodsType parentType = typeRepository.findByParentStandardTypeId(type.getParentStandardTypeId());
            type.setPath(parentType.getPath() + type.getStandardTypeId() + "|");
        }
        type.setDisabled(false);
        type.setCustomerId(-1);
        type.setTOrder(0);
        return null;
    }
}
