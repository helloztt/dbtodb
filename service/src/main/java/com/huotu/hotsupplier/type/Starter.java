package com.huotu.hotsupplier.type;

import com.huotu.hotsupplier.type.config.TypeServiceConfig;
import com.huotu.hotsupplier.type.service.HbmBrandService;
import com.huotu.hotsupplier.type.service.PropertyService;
import com.huotu.hotsupplier.type.service.PropertyValueService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by admin on 2016/1/21.
 */
public class Starter {
    private static final Log log = LogFactory.getLog(Starter.class);

    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(TypeServiceConfig.class);
        ctx.refresh();

        //开线程处理品牌
//        log.info("start do with brand");
//        PropertyValueService propertyValueService = ctx.getBean(PropertyValueService.class);
//        propertyValueService.saveBrand();
        //处理规格和规格值
//        log.info("start do with property and value");
        //处理类目
//        log.info("start do with type");

    }

}
