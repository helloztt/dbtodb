package com.huotu.hotsupplier.type.service.mysql.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mssql.HbmSpecification;
import com.huotu.hotsupplier.type.entity.mysql.Category;
import com.huotu.hotsupplier.type.entity.mysql.Property;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mssql.HbmSpecificationRepository;
import com.huotu.hotsupplier.type.repository.mysql.CategoryRepository;
import com.huotu.hotsupplier.type.repository.mysql.PropertyRepository;
import com.huotu.hotsupplier.type.repository.mysql.PropertyValueRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmGoodsTypeService;
import com.huotu.hotsupplier.type.service.mssql.HbmGoodsTypeSpecService;
import com.huotu.hotsupplier.type.service.mssql.HbmTypeBrandService;
import com.huotu.hotsupplier.type.service.mysql.CategoryService;
import com.huotu.hotsupplier.type.util.Constant;
import com.huotu.hotsupplier.type.worker.CategoryRunner;
import com.huotu.hotsupplier.type.worker.Starter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/1/22.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private HbmSpecificationRepository specRepository;
    @Autowired
    private PropertyValueRepository propertyValueRepository;
    @Autowired
    private HbmGoodsTypeService hbmGoodsTypeService;
    @Autowired
    private HbmGoodsTypeSpecService hbmGoodsTypeSpecService;
    @Autowired
    private HbmTypeBrandService hbmTypeBrandService;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Override
    public void saveType() {
        saveCategory(null, "|");
    }

    @Override
    public void saveCategory(Long parentCid, String parentPath) {
        List<Category> categoryList = categoryRepository.findByParentCid(parentCid);
        if (categoryList != null && categoryList.size() > 0) {
            categoryList.forEach(category -> {
                if(category.getCid() == 1201){
                    System.out.println();
                }
                HbmGoodsType type = hbmGoodsTypeService.saveType(category, parentPath);
                if (!type.isParent()) {
                    //如果没有子类目，则保存类目规格，规格值中间表
//                    saveTypeSpec(category.getCid(),type);
                    //如果没有子类目，则保存类目品牌中间表
//                    saveBrand(category.getCid(),type);
                    //线程处理
                    threadPoolTaskScheduler.submit(new CategoryRunner(threadPoolTaskScheduler, category.getCid(), type));
                } else {
                    //如果有子类目，则继续遍历
                    saveCategory(category.getCid(), parentPath + category.getCid() + "|");
                }
            });
        }
    }

    @Override
    public void saveTypeSpec(Long categoryId, HbmGoodsType type) {
        //找出标准类目下的所有销售属性
        List<Property> salePropertyList = propertyRepository.findByCategoryId(categoryId);
        if (salePropertyList != null && salePropertyList.size() > 0) {
            salePropertyList.forEach(property -> {
                //从缓存中获取规格对应的ID
                Integer specId = Starter.specMap.get(String.valueOf(property.getId()));
                //若缓存读取失败，则从数据库中读取
                if(specId == null){
                    specId = specRepository.findByStandardSpecId(String.valueOf(property.getId())).getSpecId();
                }
                //根据标准类目和销售属性，找出所有属性值
                List<PropertyValue> propertyValueList = propertyValueRepository.findByCategoryIdAndPropertyId(categoryId, property.getId());
                hbmGoodsTypeSpecService.saveTypeSpec(type, specId,propertyValueList);
            });
        }
    }

    @Override
    public void saveBrand(Long categoryId, HbmGoodsType type) {
        //分页找出标准类目下的所有品牌
        int page = 0;
        Page<PropertyValue> brandFirstPage = propertyValueRepository.findBrandByCategoryId(categoryId, new PageRequest(page, Constant.PAGESIZE));
        int totalPage = brandFirstPage.getTotalPages();
        if (totalPage >= 1) {
            hbmTypeBrandService.saveTypeBrand(brandFirstPage.getContent(), type);
            for (page = 1; page < totalPage; page++) {
                Page<PropertyValue> brandPage = propertyValueRepository.findBrandByCategoryId(categoryId, new PageRequest(page, Constant.PAGESIZE));
                hbmTypeBrandService.saveTypeBrand(brandPage.getContent(), type);
            }
        }
    }

}
