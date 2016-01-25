package com.huotu.hotsupplier.type.repository.mssql;

import com.huotu.hotsupplier.type.entity.mssql.HbmSpecValues;
import com.huotu.hotsupplier.type.entity.mssql.HbmTypeBrand;
import com.huotu.hotsupplier.type.entity.mssql.HbmTypeBrandPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by admin on 2016/1/22.
 */
public interface HbmTypeBrandRepository extends JpaRepository<HbmTypeBrand, HbmTypeBrandPK>, JpaSpecificationExecutor {
}
