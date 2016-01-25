/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 *
 */

package com.huotu.hotsupplier.type.entity.mssql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by admin on 2016/1/11.
 */
@Entity
@Table(name="Mall_Goods_Type_Spec")
@IdClass(HbmGoodsTypeSpecPK.class)
@Cacheable(false)
@Getter
@Setter
public class HbmGoodsTypeSpec {
    /**
     * 类型主键
     */
    @Id
    @Column(name = "Type_Id")
    private int typeId;

    @Id
    @Column(name = "Spec_Id")
    private int specId;

    @Id
    @Column(name = "Spec_Value_Id")
    private int specValueId;

    @Column(name = "GTS_Customer_Id")
    private int customerId = -1;

}
