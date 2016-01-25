package com.huotu.hotsupplier.type.entity.mysql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
@Entity
@Table(name="category")
@Cacheable(false)
@Getter
@Setter
public class Category {
    @Id
    private Long cid;
    @Column(name = "is_parent")
    private boolean isParent;
    @Column(name = "parent_cid")
    private Long parentCid;
    private String name;
    @Column(length = 20)
    private String status;
//    @OneToMany
//    @JoinTable(name = "categoryProperty",joinColumns = {@JoinColumn(name = "cid",referencedColumnName = "categoryId"),
//    @JoinColumn(name = "id",referencedColumnName = "propertyId")})
//    private List<Property> propertyList;

}
