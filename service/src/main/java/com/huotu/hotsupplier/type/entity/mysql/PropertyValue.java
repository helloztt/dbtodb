package com.huotu.hotsupplier.type.entity.mysql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by admin on 2016/1/21.
 */
@Entity
@Table(name="propertyvalue")
@Cacheable(false)
@Getter
@Setter
public class PropertyValue {
    @Id
    private Long id;
    private String name;
    private String nameAlias;
    private Long propertyId;
    private int sortOrder;
    private String status;
}
