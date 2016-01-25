package com.huotu.hotsupplier.type.repository.mysql;

import com.huotu.hotsupplier.type.entity.mysql.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor {

    List<Category> findByParentCid(Long parentCid);
}
