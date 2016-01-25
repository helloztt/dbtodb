package com.huotu.hotsupplier.type.worker;

import com.huotu.hotsupplier.type.entity.mysql.Property;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mysql.PropertyRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmBrandService;
import com.huotu.hotsupplier.type.service.mssql.HbmSpecificationService;
import com.huotu.hotsupplier.type.service.mysql.PropertyService;
import com.huotu.hotsupplier.type.service.mysql.PropertyValueService;
import com.huotu.hotsupplier.type.util.Springfactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.domain.Page;

/**
 * Created by admin on 2016/1/25.
 */
public class BrandRunner implements Runnable {
    private static final Log log = LogFactory.getLog(BrandRunner.class);

    private HbmBrandService brandService;
    private PropertyValueService propertyValueService;


    private int pageNo;
    private final AsyncTaskExecutor taskExecutor;

    public BrandRunner(AsyncTaskExecutor taskExecutor, int pageNo) {
        this.pageNo = pageNo;
        this.taskExecutor = taskExecutor;
        brandService = Springfactory.getBean(HbmBrandService.class);
        propertyValueService = Springfactory.getBean(PropertyValueService.class);
    }

    @Override
    public void run() {
        try {
            Page<PropertyValue> propertyValuePage = propertyValueService.getBrandPages(pageNo);
            if (propertyValuePage != null && propertyValuePage.getSize() != 0) {
                brandService.saveBrandList(propertyValuePage.getContent());
            }
        } catch (Exception e) {
            log.error("re submit this runnable", e);
            taskExecutor.submit(this);
        }
    }
}
