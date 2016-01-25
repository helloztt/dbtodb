package com.huotu.hotsupplier.type.worker;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mysql.Property;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mysql.PropertyRepository;
import com.huotu.hotsupplier.type.repository.mysql.PropertyValueRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmGoodsTypeSpecService;
import com.huotu.hotsupplier.type.service.mssql.HbmTypeBrandService;
import com.huotu.hotsupplier.type.util.Constant;
import com.huotu.hotsupplier.type.util.Springfactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.context.ContextLoader;

import java.util.List;

/**
 * Created by admin on 2016/1/22.
 */
public class Runner implements Runnable {
    private static final Log log = LogFactory.getLog(Runner.class);

    private PropertyRepository propertyRepository;
    private PropertyValueRepository propertyValueRepository;
    private HbmGoodsTypeSpecService hbmGoodsTypeSpecService;
    private HbmTypeBrandService hbmTypeBrandService;


    private final Long categoryId;
    private final HbmGoodsType type;
    private final AsyncTaskExecutor taskExecutor;

    public Runner(AsyncTaskExecutor taskExecutor,Long categoryId, HbmGoodsType type) {
        this.taskExecutor = taskExecutor;
        this.categoryId = categoryId;
        this.type = type;
//        ApplicationContext act = ContextLoader.getCurrentWebApplicationContext();
        propertyRepository = Springfactory.getBean(PropertyRepository.class);
        propertyValueRepository = Springfactory.getBean(PropertyValueRepository.class);
        hbmGoodsTypeSpecService = Springfactory.getBean(HbmGoodsTypeSpecService.class);
        hbmTypeBrandService = Springfactory.getBean(HbmTypeBrandService.class);

    }

    @Override
    public void run() {
        try {
            //如果没有子类目，则保存类目规格，规格值中间表
            saveTypeSpec(categoryId,type);
            //如果没有子类目，则保存类目品牌中间表
            saveBrand(categoryId, type);
        } catch (Throwable e) {
            log.error("re submit this runnable", e);
            taskExecutor.submit(this);
        }
    }

    private void saveTypeSpec(Long categoryId, HbmGoodsType type) {
        //找出标准类目下的所有销售属性
        List<Property> salePropertyList = propertyRepository.findByCategoryId(categoryId);
        if(salePropertyList != null && salePropertyList.size() > 0){
            salePropertyList.forEach(property->{
                //根据标准类目和销售属性，找出所有属性值
                List<PropertyValue> propertyValueList = propertyValueRepository.findByCategoryIdAndPropertyId(categoryId,property.getId());
                if(propertyValueList != null && propertyValueList.size() > 0){
                    propertyValueList.forEach(propertyValue->{
                        hbmGoodsTypeSpecService.saveTypeSpec(categoryId,property.getId(),propertyValue.getId());
                    });
                }
            });
        }
    }

    private void saveBrand(Long categoryId, HbmGoodsType type) {
        //分页找出标准类目下的所有品牌
        int page = 0 ;
        Page<PropertyValue> brandFirstPage = propertyValueRepository.findBrandByCategoryId(categoryId,new PageRequest(page, Constant.PAGESIZE));
        int totalPage = brandFirstPage.getTotalPages();
        if(totalPage >= 1){
            hbmTypeBrandService.saveTypeBrand(brandFirstPage.getContent(),type);
            for(page = 1; page < totalPage ; page ++){
                Page<PropertyValue> brandPage = propertyValueRepository.findBrandByCategoryId(categoryId,new PageRequest(page, Constant.PAGESIZE));
                hbmTypeBrandService.saveTypeBrand(brandFirstPage.getContent(),type);
            }
        }
    }
}
