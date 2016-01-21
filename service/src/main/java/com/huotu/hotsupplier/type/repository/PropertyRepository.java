package com.huotu.hotsupplier.type.repository;

import com.huotu.hotsupplier.type.entity.mysql.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by admin on 2016/1/21.
 */
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor {
}
