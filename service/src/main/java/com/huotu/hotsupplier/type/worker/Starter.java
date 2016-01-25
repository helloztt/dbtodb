package com.huotu.hotsupplier.type.worker;

import com.huotu.hotsupplier.type.config.MssqlServiceConfig;
import com.huotu.hotsupplier.type.config.MysqlServiceConfig;
import com.huotu.hotsupplier.type.repository.mysql.PropertyRepository;
import com.huotu.hotsupplier.type.service.mysql.CategoryService;
import com.huotu.hotsupplier.type.service.mysql.PropertyService;
import com.huotu.hotsupplier.type.service.mysql.PropertyValueService;
import com.huotu.hotsupplier.type.util.Springfactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Created by admin on 2016/1/21.
 */
public class Starter {
    private static final Log log = LogFactory.getLog(Starter.class);

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MssqlServiceConfig.class);
        ctx.register(MysqlServiceConfig.class);
        ctx.refresh();
        long start = System.currentTimeMillis();

        //处理品牌
        log.info("start do with brand");
        long brandStart = System.currentTimeMillis();
        PropertyValueService propertyValueService = ctx.getBean(PropertyValueService.class);
        propertyValueService.saveBrand();
        long brandEnd = System.currentTimeMillis();
        log.info("end do with brand,last " + (brandEnd - brandStart) + " ms");
        //处理规格和规格值
        log.info("start do with property and value");
        long propStart = System.currentTimeMillis();
        PropertyService propertyService = ctx.getBean(PropertyService.class);
        propertyService.saveProperty();
        long propEnd = System.currentTimeMillis();
        log.info("end do with prop,last " + (propEnd - propStart) + "ms");
        ThreadPoolTaskScheduler threadPoolTaskScheduler = ctx.getBean(ThreadPoolTaskScheduler.class);
        while (true){
            Thread.sleep(1000);
            if(threadPoolTaskScheduler.getActiveCount() == 0){
                break;
            }
        }
        //处理类目
        log.info("start do with category");
        CategoryService categoryService = ctx.getBean(CategoryService.class);
        categoryService.saveType();
        log.info("start do with type");
        long end = System.currentTimeMillis();
        log.info("dbtodb over , last " + (end-start) + "ms");
    }

}
