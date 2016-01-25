package com.huotu.hotsupplier.type.service.mysql.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmGoodsType;
import com.huotu.hotsupplier.type.entity.mysql.Category;
import com.huotu.hotsupplier.type.entity.mysql.Property;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mssql.HbmTypeBrandRepository;
import com.huotu.hotsupplier.type.repository.mysql.CategoryRepository;
import com.huotu.hotsupplier.type.repository.mysql.PropertyRepository;
import com.huotu.hotsupplier.type.repository.mysql.PropertyValueRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmGoodsTypeService;
import com.huotu.hotsupplier.type.service.mssql.HbmGoodsTypeSpecService;
import com.huotu.hotsupplier.type.service.mssql.HbmTypeBrandService;
import com.huotu.hotsupplier.type.service.mysql.CategoryService;
import com.huotu.hotsupplier.type.util.Constant;
import com.huotu.hotsupplier.type.worker.Runner;
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
        saveCategory(null,"|");
    }

    @Override
    public void saveCategory(Long parentCid,String parentPath) {
        List<Category> categoryList = categoryRepository.findByParentCid(parentCid);
        if(categoryList != null && categoryList.size() > 0){
            categoryList.forEach(category -> {
                HbmGoodsType type = hbmGoodsTypeService.saveType(category,parentPath);
                if(!type.isParent()){
                    //如果没有子类目，则保存类目规格，规格值中间表
//                    saveTypeSpec(category.getCid(),type);
                    //如果没有子类目，则保存类目品牌中间表
//                    saveBrand(category.getCid(),type);
                    //线程处理
                    threadPoolTaskScheduler.submit(new Runner(threadPoolTaskScheduler,category.getCid(),type));
                }else{
                    //如果有子类目，则继续遍历
                    saveCategory(category.getCid(),parentPath + category.getCid() + "|");
                }
            });
        }
    }

}
