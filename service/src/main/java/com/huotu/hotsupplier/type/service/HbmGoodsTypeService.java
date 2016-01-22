package com.huotu.hotsupplier.type.service;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mysql.Category;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.PrimitiveIterator;

/**
 * Created by admin on 2016/1/21.
 */
public interface HbmGoodsTypeService {
    HbmGoodsType saveType(Category category);
}
