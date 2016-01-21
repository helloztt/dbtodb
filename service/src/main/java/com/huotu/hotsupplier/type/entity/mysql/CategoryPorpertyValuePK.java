package com.huotu.hotsupplier.type.entity.mysql;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by admin on 2016/1/21.
 */
@Getter
@Setter
public class CategoryPorpertyValuePK implements Serializable{
    private Long categoryId;
    private Long propertyValueId;
}
