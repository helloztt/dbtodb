package com.huotu.hotsupplier.type.service.mysql.impl;

import com.huotu.hotsupplier.type.entity.mysql.Property;
import com.huotu.hotsupplier.type.repository.mysql.PropertyRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmSpecificationService;
import com.huotu.hotsupplier.type.service.mysql.PropertyService;
import com.huotu.hotsupplier.type.util.Constant;
import com.huotu.hotsupplier.type.worker.PropertyRunner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2016/1/21.
 */
@Service
public class PropertyServiceImpl implements PropertyService {
    private static final Log log = LogFactory.getLog(PropertyServiceImpl.class);
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private HbmSpecificationService hbmSpecificationService;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Override
    public void saveProperty() {
        int page = 0;
        Page<Property> propertyFirstPage = getPropertyPages(page);
        int totalPage = propertyFirstPage.getTotalPages();
        log.info("property count " + totalPage);
        if (propertyFirstPage.getContent() != null && propertyFirstPage.getContent().size() > 0) {
            hbmSpecificationService.saveSpecList(propertyFirstPage.getContent());
            for (page = 1; page < totalPage; page++) {
//                Page<Property> propertyPage = getPropertyPages(page);
//                hbmSpecificationService.saveSpecList(propertyPage.getContent());
                //线程处理
                threadPoolTaskScheduler.submit(new PropertyRunner(threadPoolTaskScheduler,page));
            }
        }
    }

    @Override
    public Page<Property> getPropertyPages(int start) {
        return propertyRepository.findBySaleProperty(true, new PageRequest(start, Constant.PAGESIZE));
    }
}
