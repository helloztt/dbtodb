package com.huotu.hotsupplier.type.entity.mssql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Brief: 规格表
 * Created by liu_qifu on 2015/12/23.
 */
@Entity
@Table(name="Mall_Specification")
@Cacheable(false)
@Getter
@Setter
public class HbmSpecification {
    /**
     * 类型主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Spec_Id")
    private Integer specId;

    /**
     * 规格名
     */
    @Column(name = "Spec_Name")
    private String specName;

    /**
     * 排序字段
     */
    @Column(name = "P_Order")
    private int order;

    /**
     * 是否禁用
     */
    private boolean disabled;

    /**
     * 布局方式
     * demo flat
     */
    @Column(name = "Spec_Show_Type")
    private String specShowType;

    /**
     * 显示方式(图片|文字)
     * demo   image text
     */
    @Column(name = "Spec_Type")
    private String specType;

    /**
     * 标准规格Id
     */
    @Column(name = "Standard_Spec_Id")
    private String standardSpecId;

    /**
     * 商户Id
     */
    @Column(name = "Customer_Id")
    private int customerId;

    /**
     * 规格值List
     */
    @Transient
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "Spec_Id")
//    @OrderBy("order ASC")
    private List<HbmSpecValues> specValues;
}
