package com.huotu.hotsupplier.type.repository.mssql;

import com.huotu.hotsupplier.type.entity.mssql.HbmBrand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by admin on 2016/1/21.
 */
public interface HbmBrandRepository extends JpaRepository<HbmBrand, Integer>, JpaSpecificationExecutor {
    HbmBrand findByStandardBrandId(String standardBrandId);

    Page<HbmBrand> findByCustomerId(int customerId,Pageable pageable);
}
