package com.huotu.hotsupplier.type.repository;

import com.huotu.hotsupplier.type.entity.mysql.CategoryBrand;
import com.huotu.hotsupplier.type.entity.mysql.CategoryBrandPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by admin on 2016/1/21.
 */
public interface CategoryBrandRepository extends JpaRepository<CategoryBrand, CategoryBrandPK>, JpaSpecificationExecutor {
}
