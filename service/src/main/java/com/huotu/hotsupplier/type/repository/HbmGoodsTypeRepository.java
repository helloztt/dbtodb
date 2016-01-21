package com.huotu.hotsupplier.type.repository;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2016/1/21.
 */
public interface HbmGoodsTypeRepository extends JpaRepository<HbmGoodsType, Integer>, JpaSpecificationExecutor {
    HbmGoodsType findByParentStandardTypeId(String parentStandardTypeId);
}
