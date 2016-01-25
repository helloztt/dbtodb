package com.huotu.hotsupplier.type.service.mssql;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mysql.Category;


/**
 * Created by admin on 2016/1/21.
 */
public interface HbmGoodsTypeService {
    HbmGoodsType saveType(Category category,String parentPath);

}
