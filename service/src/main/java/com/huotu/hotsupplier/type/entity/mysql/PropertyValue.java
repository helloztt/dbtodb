package com.huotu.hotsupplier.type.entity.mysql;

import com.huotu.hotsupplier.type.entity.mysql.Property;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by admin on 2016/1/21.
 */
@Entity
@Table(name="propertyValue")
@Cacheable(false)
@Getter
@Setter
public class PropertyValue {
    @Id
    private Long id;
    private String name;
    private String nameAlias;
    private int sortOrder;
    private String status;
//    private Long propertyId;
    @ManyToOne
    @JoinColumn(name = "propertyId")
    private Property property;
}
