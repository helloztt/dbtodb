package com.huotu.hotsupplier.type.service.mysql.impl;

import com.huotu.hotsupplier.type.config.MssqlServiceConfig;
import com.huotu.hotsupplier.type.config.MysqlServiceConfig;
import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mysql.Category;
import com.huotu.hotsupplier.type.repository.mssql.HbmGoodsTypeRepository;
import com.huotu.hotsupplier.type.repository.mysql.CategoryRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by admin on 2016/1/29.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MysqlServiceConfig.class, MssqlServiceConfig.class})
public class CategoryServiceImplTest extends TestCase {
    @Autowired
    private HbmGoodsTypeRepository hbmGoodsTypeRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
//    @Transactional(value = "mysqlTransactionManager,sqlTransactionManager")
    public void insertBrand() {
        Category category = new Category();
        category.setCid(-1L);
        category.setName("name");
        categoryRepository.save(category);

        HbmGoodsType type = new HbmGoodsType();
        type.setName("name");
        hbmGoodsTypeRepository.save(type);
//        insertType();
    }

    @Transactional(value = "sqlTransactionManager")
    public void insertType() {
    }

}