package com.huotu.hotsupplier.type.service.mssql.impl;

import com.huotu.hotsupplier.type.entity.mssql.HbmBrand;
import com.huotu.hotsupplier.type.entity.mssql.HbmTypeBrand;
import com.huotu.hotsupplier.type.entity.mysql.PropertyValue;
import com.huotu.hotsupplier.type.repository.mssql.HbmBrandRepository;
import com.huotu.hotsupplier.type.service.mssql.HbmBrandService;
import com.huotu.hotsupplier.type.util.Constant;
import com.huotu.hotsupplier.type.worker.Starter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/1/21.
 */
@Service
public class HbmBrandServiceImpl implements HbmBrandService {
    private static final Log log = LogFactory.getLog(HbmBrandServiceImpl.class);
    @Autowired
    private HbmBrandRepository brandRepository;

    //保存品牌列表
    public void saveBrandList(List<PropertyValue> brandList) {
        if (brandList != null && brandList.size() > 0) {
            List<HbmBrand> saveBrand = new ArrayList<>();
            brandList.forEach(b->{
//                HbmBrand old = brandRepository.findByStandardBrandId(String.valueOf(b.getId()));
                if(!Starter.brandMap.containsKey(String.valueOf(b.getId()))){
                    HbmBrand brand = new HbmBrand();
                    brand.setBrandName(b.getName());
                    brand.setCustomerId(-1);
                    brand.setStandardBrandId(String.valueOf(b.getId()));
                    brand.setOrderNum(b.getSortOrder());
                    if ("normal".equals(b.getStatus())) {
                        brand.setDisabled(false);
                    } else {
                        brand.setDisabled(true);
                    }
                    saveBrand.add(brand);
                    //小批量插入
                    if( saveBrand.size() > 0 && saveBrand.size() % Constant.PAGESIZE == 0){
                        saveBrands(saveBrand);
                        saveBrand.clear();
                    }
//                    brandRepository.save(brand);
                }
            });
            if(saveBrand.size() > 0){
                saveBrands(saveBrand);
            }

        }
    }

    private void saveBrands(List<HbmBrand> brandList){
        brandRepository.save(brandList).forEach(b->{
            if(!Starter.brandMap.containsKey(b.getStandardBrandId())){
                Starter.brandMap.put(b.getStandardBrandId(),b.getBrandId());
            }
        });
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBrandList() {
        //将数据库已存在的标准品牌放入缓存中
        long start = System.currentTimeMillis();
        int page = 0;
        Page<HbmBrand> firstBrandList = brandRepository.findByCustomerId(-1,new PageRequest(page, Constant.READPAGESIZE));
        int totalPage = firstBrandList.getTotalPages();
        log.info("brand total page " + totalPage);
        if(firstBrandList != null && totalPage > 0){
            firstBrandList.getContent().forEach(b->{
                if(!Starter.brandMap.containsKey(b.getStandardBrandId())){
                    Starter.brandMap.put(b.getStandardBrandId(),b.getBrandId());
                }
            });
            for(page = 1;page<totalPage ; page ++){
                Page<HbmBrand> brandList = brandRepository.findByCustomerId(-1,new PageRequest(page,Constant.READPAGESIZE));
                brandList.getContent().forEach(b->{
                    if(!Starter.brandMap.containsKey(b.getStandardBrandId())){
                        Starter.brandMap.put(b.getStandardBrandId(),b.getBrandId());
                    }
                });
                log.info("get brand page" + page);
            }
        }
        long end = System.currentTimeMillis();
        log.info("get brand list over totalpage " + totalPage + " cost " + (end-start) + " ms");
    }

    @Override
    public long getBrandCount() {
        return brandRepository.count();
    }
}
