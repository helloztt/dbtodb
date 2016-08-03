package com.huotu.hotsupplier.type.worker;

import com.huotu.hotsupplier.type.service.mssql.*;
import com.sun.glass.ui.SystemClipboard;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by helloztt on 2016/3/2.
 */
public class TestRunner implements Runnable {
    private static final Log log = LogFactory.getLog(TestRunner.class);
    private HbmBrandService brandService;
    private HbmSpecificationService specificationService;
    private HbmSpecValuesService specValuesService;
    private HbmGoodsTypeService typeService;
    private HbmGoodsTypeSpecService typeSpecService;
    private HbmTypeBrandService typeBrandService;

    private boolean isRun;

    public TestRunner(HbmBrandService brandService, HbmSpecificationService specificationService, HbmSpecValuesService specValuesService, HbmGoodsTypeService typeService, HbmGoodsTypeSpecService typeSpecService, HbmTypeBrandService typeBrandService, boolean isRun) {
        this.brandService = brandService;
        this.specificationService = specificationService;
        this.specValuesService = specValuesService;
        this.typeService = typeService;
        this.typeSpecService = typeSpecService;
        this.typeBrandService = typeBrandService;
        this.isRun = isRun;
    }

    private long maxBrand = 0L;
    private long maxSpec = 0L;
    private long maxSpecValue = 0L;
    private long maxType = 0L;
    private long maxTypeSpec = 0L;
    private long maxTypeBrand = 0L;


    @Override
    public void run() {
        long start, end;
        long num = 0;
        while (isRun) {
            num++;
            start = System.currentTimeMillis();
            brandService.getBrandCount();
            end = System.currentTimeMillis();
            if (end - start > maxBrand) {
                maxBrand = end - start;
            }

            start = System.currentTimeMillis();
            specificationService.getSpecCount();
            end = System.currentTimeMillis();
            if (end - start > maxSpec) {
                maxSpec = end - start;
            }

            start = System.currentTimeMillis();
            specValuesService.getSpecValueCount();
            end = System.currentTimeMillis();
            if (end - start > maxSpecValue) {
                maxSpecValue = end - start;
            }

            start = System.currentTimeMillis();
            typeService.getTypeCount();
            end = System.currentTimeMillis();
            if (end - start > maxType) {
                maxType = end - start;
            }

            start = System.currentTimeMillis();
            typeSpecService.getTypeSpecCount();
            end = System.currentTimeMillis();
            if (end - start > maxTypeSpec) {
                maxTypeSpec = end - start;
            }

            start = System.currentTimeMillis();
            typeBrandService.getTypeBrandCount();
            end = System.currentTimeMillis();
            if (end - start > maxTypeBrand) {
                maxTypeBrand = end - start;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (num % 1000 == 0) {
                num = 0;
                print();
            }
        }
        print();
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    private void print() {
        log.info("brand size:" + StartRunner.brandMap.size());
        log.info("spec size:" + StartRunner.specMap.size());
        log.info("spec value size:" + StartRunner.specValueMap.size());
        log.info("maxBrand:" + maxBrand/1000);
        log.info("maxSpec:" + maxSpec/1000);
        log.info("maxSpecValue:" + maxSpecValue/1000);
        log.info("maxType:" + maxType/1000);
        log.info("maxTypeSpec:" + maxTypeSpec/1000);
        log.info("maxTypeBrand:" + maxTypeBrand/1000);
    }
}
