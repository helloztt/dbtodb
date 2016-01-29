package com.huotu.hotsupplier.type.worker;

import com.huotu.hotsupplier.type.config.MssqlServiceConfig;
import com.huotu.hotsupplier.type.config.MysqlServiceConfig;
import com.huotu.hotsupplier.type.service.mssql.HbmBrandService;
import com.huotu.hotsupplier.type.service.mssql.HbmSpecValuesService;
import com.huotu.hotsupplier.type.service.mssql.HbmSpecificationService;
import com.huotu.hotsupplier.type.service.mysql.CategoryService;
import com.huotu.hotsupplier.type.service.mysql.PropertyService;
import com.huotu.hotsupplier.type.service.mysql.PropertyValueService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/1/21.
 */
public class Starter {
    private static final Log log = LogFactory.getLog(Starter.class);

    //key:standard id,value: key id
    public static Map<String,Integer> brandMap = new HashMap<>();
    public static Map<String,Integer> specMap = new HashMap<>();
    public static Map<String,Integer> specValueMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MssqlServiceConfig.class);
        ctx.register(MysqlServiceConfig.class);
        ctx.refresh();
        ThreadPoolTaskScheduler threadPoolTaskScheduler = ctx.getBean(ThreadPoolTaskScheduler.class);
        long start = System.currentTimeMillis();

        //获取已保存的品牌
        HbmBrandService brandService = ctx.getBean(HbmBrandService.class);
        brandService.getBrandList();
        //处理品牌
        log.info("start do with brand");
        long brandStart = System.currentTimeMillis();
        PropertyValueService propertyValueService = ctx.getBean(PropertyValueService.class);
        propertyValueService.saveBrand();
        while (true){
            Thread.sleep(500);
            if(threadPoolTaskScheduler.getActiveCount() == 0){
                break;
            }
        }
        long brandEnd = System.currentTimeMillis();
        log.info("end do with brand,last " + (brandEnd - brandStart) + " ms");
        //获取已保存的规格和规格值
        HbmSpecificationService specService = ctx.getBean(HbmSpecificationService.class);
        HbmSpecValuesService specValuesService = ctx.getBean(HbmSpecValuesService.class);
        specService.getSpecList();
        specValuesService.getSpecValueList();
        //处理规格和规格值
        log.info("start do with property and value");
        long propStart = System.currentTimeMillis();
        PropertyService propertyService = ctx.getBean(PropertyService.class);
        propertyService.saveProperty();
        while (true){
            Thread.sleep(500);
            if(threadPoolTaskScheduler.getActiveCount() == 0){
                break;
            }
        }
        long propEnd = System.currentTimeMillis();
        log.info("end do with prop,last " + (propEnd - propStart) + "ms");
        //处理类目
        log.info("start do with category");
        CategoryService categoryService = ctx.getBean(CategoryService.class);
        categoryService.saveType();
        while (true){
            Thread.sleep(500);
            if(threadPoolTaskScheduler.getActiveCount() == 0){
                break;
            }
        }
        long end = System.currentTimeMillis();
        log.info("dbtodb over , last " + (end-start) + "ms");
    }

}
