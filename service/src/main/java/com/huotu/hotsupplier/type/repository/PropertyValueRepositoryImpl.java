package com.huotu.hotsupplier.type.repository;

import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
public class PropertyValueRepositoryImpl implements PropertyValueRepositoryCustom {
    @PersistenceContext(unitName = "mysqlEntity")
    private EntityManager entityManager;

    public List<PropertyValue> findBrandList(int start,int limit) {
        String sql = "SELECT a FROM PropertyValue a,Property b WHERE a.propertyId = b.id AND b.name LIKE '%Æ·ÅÆ%'";
        List<PropertyValue> propertyValueList = entityManager.createQuery(sql)
                .setFirstResult(start).setMaxResults(limit).getResultList();
        return propertyValueList;
    }
}
