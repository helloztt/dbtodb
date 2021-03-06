/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 *
 */

package com.huotu.hotsupplier.type.repository.mssql;

import com.huotu.hotsupplier.type.entity.mssql.HbmSpecValues;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by admin on 2016/1/5.
 */
public interface HbmSpecValuesRepository extends JpaRepository<HbmSpecValues, Integer>, JpaSpecificationExecutor {
    HbmSpecValues findByStandardSpecValueId(String standardSpecValueId);

    Page<HbmSpecValues> findByCustomerId(int customerId,Pageable pageable);

    long countByCustomerId(int customerId);
}
