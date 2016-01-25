package com.huotu.hotsupplier.type.repository.mysql;

import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
public interface PropertyValueRepository extends JpaRepository<PropertyValue, Long>, JpaSpecificationExecutor {

    Page<PropertyValue> findByProperty_NameLike(String name,Pageable pageable);

    Page<PropertyValue> findByProperty_IdAndProperty_SaleProperty(Long propertyId,boolean saleProperty,Pageable pageable);

    @Query("SELECT b FROM CategoryPropertyValue a,PropertyValue b WHERE  a.propertyValueId = b.id AND a.categoryId = ?1 AND b.property.id = ?2")
    List<PropertyValue> findByCategoryIdAndPropertyId(Long categoryId,Long propertyId);
    @Query("SELECT b FROM CategoryPropertyValue a,PropertyValue b WHERE a.propertyValueId = b.id AND a.categoryId = ?1 AND b.property.name like '%品牌%'")
    Page<PropertyValue> findBrandByCategoryId(Long categoryId,Pageable pageable);

}
