package com.huotu.hotsupplier.type.entity.mssql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by admin on 2016/1/22.
 */
@Entity
@Table(name="Mall_Type_Brand")
@IdClass(HbmTypeBrandPK.class)
@Cacheable(false)
@Getter
@Setter
public class HbmTypeBrand {
    @Id
    @Column(name = "Type_Id")
    private int typeId;
    @Id
    @Column(name = "Brand_Id")
    private int brandId;
    @Column(name = "Brand_Order")
    private int brandOrder = 0;
}
