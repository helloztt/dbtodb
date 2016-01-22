package com.huotu.hotsupplier.type.entity.mysql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by admin on 2016/1/21.
 */
@Entity
@Table(name="categoryPropertyValue")
@IdClass(CategoryPorpertyValuePK.class)
@Cacheable(false)
@Getter
@Setter
public class CategoryPropertyValue {
    @Id
    private Long categoryId;
    @Id
    private Long propertyValueId;
}
