package com.huotu.hotsupplier.type.repository.mssql;

import com.huotu.hotsupplier.type.entity.mssql.HbmSpecValues;
import com.huotu.hotsupplier.type.entity.mssql.HbmTypeBrand;
import com.huotu.hotsupplier.type.entity.mssql.HbmTypeBrandPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by admin on 2016/1/22.
 */
public interface HbmTypeBrandRepository extends JpaRepository<HbmTypeBrand, HbmTypeBrandPK>, JpaSpecificationExecutor {
    HbmTypeBrand findByTypeIdAndBrandId(int typeId,int brandId);

    @Query("select count(a) from HbmTypeBrand a left join HbmGoodsType b on a.typeId = b.typeId where b.customerId = ?1")
    long countByCustomerId(int customerId);
}
