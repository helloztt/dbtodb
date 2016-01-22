package com.huotu.hotsupplier.type.service.impl;

import com.huotu.hotsupplier.type.entity.mysql.Property;
import com.huotu.hotsupplier.type.repository.PropertyRepository;
import com.huotu.hotsupplier.type.service.HbmSpecificationService;
import com.huotu.hotsupplier.type.service.PropertyService;
import com.huotu.hotsupplier.type.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2016/1/21.
 */
@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private HbmSpecificationService hbmSpecificationService;

    @Override
    public void saveProperty() {
        int page = 0 ;
        Page<Property> propertyFirstPage = getPropertyPages(page);
        int totalPage = propertyFirstPage.getTotalPages();
        if(propertyFirstPage.getContent() != null && propertyFirstPage.getContent().size() > 0){
            hbmSpecificationService.saveSpecList(propertyFirstPage.getContent());
            for(page = 1;page < totalPage ; page ++){
                Page<Property> propertyPage = getPropertyPages(page);
                hbmSpecificationService.saveSpecList(propertyPage.getContent());
            }
        }
    }
    @Override
    public Page<Property> getPropertyPages(int start) {
        return propertyRepository.findBySaleProperty(true,new PageRequest(start, Constant.PAGESIZE));
    }
}
