package com.huotu.hotsupplier.type.repository.mysql;

import com.huotu.hotsupplier.type.entity.mysql.CategoryProperty;
import com.huotu.hotsupplier.type.entity.mysql.CategoryPropertyPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by admin on 2016/1/22.
 */
public interface CategoryPropertyRepository extends JpaRepository<CategoryProperty, CategoryPropertyPK>, JpaSpecificationExecutor {
}
