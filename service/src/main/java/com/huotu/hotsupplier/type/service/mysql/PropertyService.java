package com.huotu.hotsupplier.type.service.mysql;

import com.huotu.hotsupplier.type.entity.mysql.Property;
import org.springframework.data.domain.Page;

/**
 * Created by admin on 2016/1/21.
 */
public interface PropertyService {

    void saveProperty();

    Page<Property> getPropertyPages(int start);
}
