package com.huotu.hotsupplier.type.repository;

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
public interface PropertyValueRepository extends JpaRepository<PropertyValue, Long>, PropertyValueRepositoryCustom {

    Page<PropertyValue> findByProperty_NameLike(String name,Pageable pageable);

    Page<PropertyValue> findByProperty_IdAndProperty_SaleProperty(Long propertyId,boolean saleProperty,Pageable pageable);

}
