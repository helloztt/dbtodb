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
import java.util.List;

/**
 * 商品类型
 * Created by ztt on 2015/12/23.
 */
@Entity
@Table(name="Mall_Goods_Type")
@Cacheable(false)
@Getter
@Setter
public class HbmGoodsType {
    /**
     * 类型主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Type_Id")
    private Integer typeId;
    /**
     * 类型名称
     */
    private String name;
    /**
     * 是否是实体商品类型
     */
    @Column(name = "Is_Physical")
    private boolean isPhysical = true;
    /**
     * 标准商品类目ID
     */
    @Column(name = "Standard_Type_Id")
    private String standardTypeId;
    /**
     * 是否有子类目
     */
    @Column(name = "Is_Parent")
    private boolean isParent;
    /**
     * 父类目ID
     */
    @Column(name = "Parent_Standard_Type_Id")
    private String parentStandardTypeId;
    /**
     * 标准类目路径
     */
    private String path;
    /**
     * 是否有效
     */
    private boolean disabled;
    /**
     * 分销商ID
     */
    @Column(name = "Customer_Id")
    private int customerId;

    @Column(name = "T_Order")
    private int tOrder;

    @OneToMany
    @JoinTable(name = "Mall_Type_Brand",joinColumns = {@JoinColumn(name = "Type_Id",referencedColumnName = "Type_Id")},
    inverseJoinColumns = {@JoinColumn(name = "Brand_Id",referencedColumnName = "Brand_Id")})
    @OrderBy("orderNum ASC")

    private List<HbmBrand> brandList;

//    @OneToMany
//    @JoinTable(name = "Mall_Goods_Type_Spec",joinColumns = {@JoinColumn(name = "Type_Id",referencedColumnName = "Type_Id")},
//    inverseJoinColumns = @JoinColumn(name = "Spec_Id",referencedColumnName = "Spec_Id"))
//    @OrderBy("order ASC")
    @Transient
    private List<HbmSpecification> specList;

    /**
     * 类目名路径
     */
    @Transient
    private String pathStr;
}
