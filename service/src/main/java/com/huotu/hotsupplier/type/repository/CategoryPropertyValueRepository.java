package com.huotu.hotsupplier.type.repository;

import com.huotu.hotsupplier.type.entity.mysql.CategoryPorpertyValuePK;
import com.huotu.hotsupplier.type.entity.mysql.CategoryPropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by admin on 2016/1/21.
 */
public interface CategoryPropertyValueRepository extends JpaRepository<CategoryPropertyValue, CategoryPorpertyValuePK>, JpaSpecificationExecutor {
}
