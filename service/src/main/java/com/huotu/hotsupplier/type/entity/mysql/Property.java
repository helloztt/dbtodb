package com.huotu.hotsupplier.type.entity.mysql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
@Entity
@Table(name="property")
@Cacheable(false)
@Getter
@Setter
public class Property {
    @Id
    private Long id;
    private String name;
    private int sortOrder;
    private String status;
}
