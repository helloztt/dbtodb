package com.huotu.hotsupplier.type.worker;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mysql.Property;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mysql.PropertyRepository;
import com.huotu.hotsupplier.type.repository.mysql.PropertyValueRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmGoodsTypeSpecService;
import com.huotu.hotsupplier.type.service.mssql.HbmTypeBrandService;
import com.huotu.hotsupplier.type.service.mysql.CategoryService;
import com.huotu.hotsupplier.type.util.Constant;
import com.huotu.hotsupplier.type.util.Springfactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by admin on 2016/1/22.
 */
public class CategoryRunner implements Runnable {
    private static final Log log = LogFactory.getLog(CategoryRunner.class);

    private CategoryService categoryService;


    private final Long categoryId;
    private final HbmGoodsType type;
    private final AsyncTaskExecutor taskExecutor;

    public CategoryRunner(AsyncTaskExecutor taskExecutor, Long categoryId, HbmGoodsType type) {
        this.taskExecutor = taskExecutor;
        this.categoryId = categoryId;
        this.type = type;
        categoryService = Springfactory.getBean(CategoryService.class);

    }

    @Override
    public void run() {
        try {
            //如果没有子类目，则保存类目规格，规格值中间表
            categoryService.saveTypeSpec(categoryId, type);
            //如果没有子类目，则保存类目品牌中间表
            categoryService.saveBrand(categoryId, type);
        } catch (Throwable e) {
            log.error("re submit this runnable", e);
            taskExecutor.submit(this);
        }
    }
}
