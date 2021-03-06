package com.huotu.hotsupplier.type.service.mysql.impl;

import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mysql.PropertyValueRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmBrandService;
import com.huotu.hotsupplier.type.service.mysql.PropertyValueService;
import com.huotu.hotsupplier.type.util.Constant;
import com.huotu.hotsupplier.type.worker.BrandRunner;
import com.huotu.hotsupplier.type.worker.CategoryRunner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2016/1/22.
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    private static final Log log = LogFactory.getLog(PropertyValueServiceImpl.class);
    @Autowired
    private PropertyValueRepository propertyValueRepository;
    @Autowired
    private HbmBrandService brandService;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Override
    public void saveBrand() {
        int page = 0;
        Page<PropertyValue> propertyValueFirstPage = getBrandPages(page);
        int totalPage = propertyValueFirstPage.getTotalPages();
        log.info("brand count " + totalPage);
        if (totalPage >= 1) {
            brandService.saveBrandList(propertyValueFirstPage.getContent());
            for (page = 1; page < totalPage; page++) {
//                Page<PropertyValue> propertyValuePage = getBrandPages(page);
//                brandService.saveBrandList(propertyValuePage.getContent());
                //线程处理
                threadPoolTaskScheduler.submit(new BrandRunner(threadPoolTaskScheduler,page));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Page<PropertyValue> getBrandPages(int start) {
        return propertyValueRepository.findByProperty_NameLike("%品牌%", new PageRequest(start, Constant.READPAGESIZE));
    }
}
