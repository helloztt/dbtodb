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
 * 商品品牌
 * Created by ztt on 2015/12/23.
 */
@Entity
@Table(name="Mall_Brand")
@Cacheable(false)
@Getter
@Setter
public class HbmBrand {
    /**
     * 品牌主键ID
     * 自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Brand_Id")
    private Integer brandId;
    /**
     * 品牌名称
     * name
     */
    @Column(name = "Brand_Name")
    private String brandName;
    /**
     * 品牌URL
     */
    @Column(name = "Brand_Url")
    private String brandUrl = "";
    /**
     * 品牌描述
     */
    @Column(name = "Brand_Desc")
    private String brandDesc = "";
    /**
     * 品牌LOGO
     */
    @Column(name = "Brand_Logo")
    private String brandLogo = "";
    /**
     *
     */
    @Column(name = "Brand_Keywords")
    private String brandKeywords = "";

    private boolean disabled = false;
    /**
     * 分销商编号
     */
    @Column(name = "Customer_Id")
    private int customerId = -1;
    /**
     * 标准品牌ID
     */
    @Column(name = "Standard_Brand_Id")
    private String standardBrandId;
    /**
     * 排序
     */
    private int orderNum = 0;
}
