package com.huotu.hotsupplier.type.worker;

import com.huotu.hotsupplier.type.config.MssqlServiceConfig;
import com.huotu.hotsupplier.type.config.MysqlServiceConfig;
import com.huotu.hotsupplier.type.service.mssql.*;
import com.huotu.hotsupplier.type.service.mysql.CategoryService;
import com.huotu.hotsupplier.type.service.mysql.PropertyService;
import com.huotu.hotsupplier.type.service.mysql.PropertyValueService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

/**
 * Created by helloztt on 2016/8/3.
 */
public class StartRunner extends TimerTask {
    private static final Log log = LogFactory.getLog(StartRunner.class);

    //key:standard id,value: key id
    public static Map<String, Integer> brandMap = new HashMap<>();
    public static Map<String, Integer> specMap = new HashMap<>();
    public static Map<String, Integer> specValueMap = new HashMap<>();
    @Override
    public void run(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MssqlServiceConfig.class);
        ctx.register(MysqlServiceConfig.class);
        ctx.refresh();
//        if(args.length >= 1){
//            String mode = args[0];
        // 模式0为导出所有数据
//            if("0".equals(mode)){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = ctx.getBean(ThreadPoolTaskScheduler.class);
        long start = System.currentTimeMillis();

        HbmBrandService brandService = ctx.getBean(HbmBrandService.class);
        HbmSpecificationService specService = ctx.getBean(HbmSpecificationService.class);
        HbmSpecValuesService specValuesService = ctx.getBean(HbmSpecValuesService.class);
        HbmGoodsTypeService typeService = ctx.getBean(HbmGoodsTypeService.class);
        HbmGoodsTypeSpecService typeSpecService = ctx.getBean(HbmGoodsTypeSpecService.class);
        HbmTypeBrandService typeBrandService = ctx.getBean(HbmTypeBrandService.class);

        PropertyValueService propertyValueService = ctx.getBean(PropertyValueService.class);
        PropertyService propertyService = ctx.getBean(PropertyService.class);
        CategoryService categoryService = ctx.getBean(CategoryService.class);

        //启动线程，持续获取数据，测试导入过程中对数据库查询的影响
        /*TestRunner testRunner = new TestRunner(brandService,specService,specValuesService,typeService,typeSpecService,typeBrandService,true);
        new Thread(testRunner).start();*/

        //获取已保存的品牌
        log.info("start get brand");
        brandService.getBrandList();
        //处理品牌
        log.info("start do with brand");
        long brandStart = System.currentTimeMillis();
        propertyValueService.saveBrand();
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            if (threadPoolTaskScheduler.getActiveCount() == 0) {
                break;
            }
        }
        long brandEnd = System.currentTimeMillis();
        log.info("end do with brand,last " + (brandEnd - brandStart) + " ms");
        //获取已保存的规格和规格值
        specService.getSpecList();
        specValuesService.getSpecValueList();
        //处理规格和规格值
        log.info("start do with property and value");
        long propStart = System.currentTimeMillis();
        propertyService.saveProperty();
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            if (threadPoolTaskScheduler.getActiveCount() == 0) {
                break;
            }
        }
        long propEnd = System.currentTimeMillis();
        log.info("end do with prop,last " + (propEnd - propStart) + "ms");
        //处理类目
        log.info("start do with category");
        categoryService.saveType();
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            if (threadPoolTaskScheduler.getActiveCount() == 0) {
                break;
            }
        }
        /*testRunner.setRun(false);*/
        long end = System.currentTimeMillis();
        log.info("dbtodb over , last " + (end - start) + "ms");
            /*}else{
                //保存部分数据
                for(int i = 1 ; i < args.length; i++){
                    String propertyId = args[i];
                    CategoryService categoryService = ctx.getBean(CategoryService.class);
                    categoryService.saveSome(Long.parseLong(propertyId));
                }
            }
        }*/
    }
}
