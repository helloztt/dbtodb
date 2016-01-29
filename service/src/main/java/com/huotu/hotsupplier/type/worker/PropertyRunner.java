package com.huotu.hotsupplier.type.worker;

import com.huotu.hotsupplier.type.entity.mysql.Property;
import com.huotu.hotsupplier.type.service.mssql.HbmSpecValuesService;
import com.huotu.hotsupplier.type.service.mssql.HbmSpecificationService;
import com.huotu.hotsupplier.type.service.mysql.PropertyService;
import com.huotu.hotsupplier.type.util.Springfactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.domain.Page;

/**
 * Created by admin on 2016/1/25.
 */
public class PropertyRunner implements Runnable{
    private static final Log log = LogFactory.getLog(CategoryRunner.class);

    private HbmSpecificationService hbmSpecificationService;
    private HbmSpecValuesService hbmSpecValuesService;
    private PropertyService propertyService;

    private int pageNo;
    private final AsyncTaskExecutor taskExecutor;

    public PropertyRunner(AsyncTaskExecutor taskExecutor, int pageNo) {
        this.taskExecutor = taskExecutor;
        this.pageNo = pageNo;
        propertyService = Springfactory.getBean(PropertyService.class);
        hbmSpecificationService = Springfactory.getBean(HbmSpecificationService.class);
        hbmSpecValuesService = Springfactory.getBean(HbmSpecValuesService.class);
    }

    @Override
    public void run() {
        try {
            Page<Property> propertyPage = propertyService.getPropertyPages(pageNo);
            hbmSpecificationService.saveSpecList(propertyPage.getContent());
        }catch (Exception e){
            log.error("re submit this runnable", e);
//            taskExecutor.submit(this);
        }

    }
}
