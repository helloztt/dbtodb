package com.huotu.hotsupplier.type.repository.mysql;

import com.huotu.hotsupplier.type.entity.mysql.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor {
    Page<Property> findBySaleProperty(boolean saleProperty,Pageable pageable);


    @Query("SELECT b FROM CategoryProperty a,Property b WHERE a.propertyId = b.id AND a.categoryId = ?1 AND b.saleProperty = true ")
    List<Property> findByCategoryId(Long categoryId);


}
