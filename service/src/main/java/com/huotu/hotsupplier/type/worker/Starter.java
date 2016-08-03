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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Created by admin on 2016/1/21.
 */
public class Starter {
    private static final Log log = LogFactory.getLog(Starter.class);

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timer timer = new Timer();
        String startTimeStr = null;
        if(args == null || args.length == 0){
            System.out.print("请输入开始时间(格式 yyyy-MM-dd HH:mm:ss):");
            byte[] b = new byte[1024];
            int count = System.in.read(b);
            startTimeStr = new String(b, 0, count);
        }else{
            startTimeStr = args[0];
        }
        Date date = sf.parse(startTimeStr);
        log.info("预计开始时间：" + startTimeStr);
        timer.schedule(new StartRunner(), date);
    }

}
