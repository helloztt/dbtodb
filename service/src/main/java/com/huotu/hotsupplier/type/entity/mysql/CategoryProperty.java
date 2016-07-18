package com.huotu.hotsupplier.type.entity.mysql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by admin on 2016/1/22.
 */
@Entity
@Table(name="categoryProperty")
@IdClass(CategoryPropertyPK.class)
@Cacheable(false)
@Getter
@Setter
public class CategoryProperty {
    @Id
    private Long categoryId;
    @Id
    private Long propertyId;
    private boolean saleProperty;
}
