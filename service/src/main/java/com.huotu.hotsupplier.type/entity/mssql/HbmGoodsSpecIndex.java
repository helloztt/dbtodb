/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 *
 */

package com.huotu.hotsupplier.type.entity.mssql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by lqf on 2016/1/11.
 */
@Entity
@Table(name="Mall_Supplier_Goods_Spec_Index")
@IdClass(HbmGoodsSpecIndexPK.class)
@Cacheable(false)
@Getter
@Setter
public class HbmGoodsSpecIndex {
    /**
     * 商品类型Id
     */
    @Column(name = "Type_Id")
    private int typeId;
    /**
     * 规格Id
     */
    @Column(name = "Spec_Id")
    private int specId;
    /**
     * 规格值Id
     */
    @Id
    @Column(name = "Spec_Value_Id")
    private Integer specValueId;
    /**
     * 规格值
     */
    @Id
    @Column(name = "Spec_Value")
    private String specValue;
    /**
     * 商品Id
     */
    @Column(name = "Supplier_Goods_Id")
    private int goodsId;
    /**
     * 货品Id
     */
    @Id
    @Column(name = "Supplier_Product_Id")
    private Integer productId;
}
