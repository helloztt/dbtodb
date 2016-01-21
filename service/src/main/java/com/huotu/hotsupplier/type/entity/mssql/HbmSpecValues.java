package com.huotu.hotsupplier.type.entity.mssql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Brief: 规格值表
 * Created by liu_qifu on 2015/12/23.
 */
@Entity
@Table(name="Mall_Spec_Values")
@Cacheable(false)
@Getter
@Setter
public class HbmSpecValues {
    /**
     * 类型主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Spec_Value_Id")
    private Integer id;

    @Column(name = "Spec_Id")
    private int specId;

    /**
     * 规格值
     */
    @Column(name = "Spec_Value")
    private String value;

    /**
     * 规格值别名
     */
    private String alias;

    /**
     * 规格图片
     */
    @Column(name = "Spec_Image")
    private String image = "";

    /**
     * 排序字段
     */
    @Column(name = "P_Order")
    private int order;

    /**
     * 商户Id
     */
    @Column(name = "SV_Customer_Id")
    private int customerId = -1;

    @Column(name = "Supplier_Id")
    private int supplierId = 0;

    @Column(name = "Supplier_Spec_Value_Id")
    private int supplierSpecValueId = 0 ;

    /**
     * 标准规格值Id
     */
    @Column(name = "Standard_Spec_Value_Id")
    private String standardSpecValueId;
}
