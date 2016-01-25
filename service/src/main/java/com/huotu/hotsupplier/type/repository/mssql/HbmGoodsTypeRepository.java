package com.huotu.hotsupplier.type.repository.mssql;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by admin on 2016/1/21.
 */
public interface HbmGoodsTypeRepository extends JpaRepository<HbmGoodsType, Integer>, JpaSpecificationExecutor {
    HbmGoodsType findByStandardTypeId(String standardTypeId);

}
