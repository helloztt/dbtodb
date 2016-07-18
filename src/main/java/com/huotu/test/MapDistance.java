package com.huotu.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by helloztt on 2016/5/5.
 */
public class MapDistance {
    private static double EARTH_RADIUS = 6370693.5;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
     * 参数为String类型
     *
     * @param lat1Str 用户经度
     * @param lng1Str 用户纬度
     * @param lat2Str 商家经度
     * @param lng2Str 商家纬度
     * @return
     */
    public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
        Double lat1 = Double.parseDouble(lat1Str);
        Double lng1 = Double.parseDouble(lng1Str);
        Double lat2 = Double.parseDouble(lat2Str);
        Double lng2 = Double.parseDouble(lng2Str);

        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lng1) - rad(lng2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000) / 10000;
        String distanceStr = distance + "";
        distanceStr = distanceStr.
                substring(0, distanceStr.indexOf("."));

        return distanceStr;
    }

    /**
     * 获取当前用户一定距离以内的经纬度值
     * 单位米 return minLat
     * 最小经度 minLng
     * 最小纬度 maxLat
     * 最大经度 maxLng
     * 最大纬度 minLat
     */
    public static Map getAround(String latStr, String lngStr, String raidus) {
        Map map = new HashMap();

        Double latitude = Double.parseDouble(latStr);// 传值给经度
        Double longitude = Double.parseDouble(lngStr);// 传值给纬度

        Double degree = (24901 * 1609) / 360.0; // 获取每度
        double raidusMile = Double.parseDouble(raidus);

        Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180)) + "").replace("-", ""));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        //获取最小经度
        Double minLat = longitude - radiusLng;
        // 获取最大经度
        Double maxLat = longitude + radiusLng;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        // 获取最小纬度
        Double minLng = latitude - radiusLat;
        // 获取最大纬度
        Double maxLng = latitude + radiusLat;

        map.put("minLat", minLat + "");
        map.put("maxLat", maxLat + "");
        map.put("minLng", minLng + "");
        map.put("maxLng", maxLng + "");

        return map;
    }

    /**
     * 计算地球上任意两点(经纬度)距离
     *
     * @param long1 第一点经度
     * @param lat1  第一点纬度
     * @param long2 第二点经度
     * @param lat2  第二点纬度
     * @return 返回距离 单位：米
     */
    public static double Distance(double long1, double lat1, double long2,
                                  double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    public static double getShortDistance(double lon1, double lat1, double lon2, double lat2) {
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * Math.PI / 180.0;
        ns1 = lat1 * Math.PI / 180.0;
        ew2 = lon2 * Math.PI / 180.0;
        ns2 = lat2 * Math.PI / 180.0;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > Math.PI) {
            dew = 2 * Math.PI - dew;
        } else if (dew + Math.PI < 0) {
            dew = 2 * Math.PI + dew;
        }
        dx = EARTH_RADIUS * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = EARTH_RADIUS * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    /**
     * 获取当前用户一定距离以内的经纬度值
     * 单位米 return minLat
     * 最小经度 minLng
     * 最小纬度 maxLat
     * 最大经度 maxLng
     * 最大纬度 minLat
     */
    public static Map getShortAround(double lon,double lat,double distinct){
        Map map = new HashMap();
        double minLon = 0 ,maxLon = 0 , minLat =0 ,maxLat = 0;
        //计算最大，最小经度
        double temp =  distinct* 180 /(Math.PI * EARTH_RADIUS * Math.cos(lat * Math.PI / 180));
        minLon = Math.abs(lon - temp);
        maxLon = Math.abs(lon + temp);
        map.put("minLon",minLon);
        map.put("maxLon",maxLon);
        //计算最大，最小纬度
        double temp2 = distinct * 180 /(Math.PI * EARTH_RADIUS);
        minLat = Math.abs(lat - temp2);
        maxLat = Math.abs(lat + temp2);
        map.put("minLat",minLat);
        map.put("maxLat",maxLat);
        return map;
    }

    public static void main(String[] args) {
        //济南国际会展中心经纬度：117.11811  36.68484
        //趵突泉：117.00999000000002  36.66123
        System.out.println(getDistance("117.11811", "36.68484", "117.00999000000002", "36.66123"));
        System.out.println(Distance(117.11811, 36.68484, 117.00999000000002, 36.66123));
        System.out.println(getShortDistance(117.11811, 36.68484, 117.00999000000002, 36.66123));
        //智慧E谷 120.228826,30.214399
        //春波南苑 120.219884,30.193799
        System.out.println(getDistance("120.228826", "30.214399", "120.219884", "30.193799"));
        System.out.println(Distance(120.228826, 30.214399, 120.219884, 30.193799));
        System.out.println(getShortDistance(120.228826, 30.214399, 120.219884, 30.193799));

        System.out.println(getAround("117.11811", "36.68484", "13000"));
        System.out.println(getShortAround(117.11811, 36.68484, 13000));
        System.out.println(getShortDistance(117.11811, 36.68484, 117.11811, 36.801757433505486));
        System.out.println(getShortDistance(117.11811, 36.68484, 117.11811, 36.567922566494516));
        System.out.println(getShortDistance(117.11811, 36.68484, 116.97231561918967, 36.68484));
        System.out.println(getShortDistance(117.11811, 36.68484, 117.26390438081033, 36.68484));
        //117.01028712333508(Double), 117.22593287666493(Double),
        //36.44829619896034(Double), 36.92138380103966(Double)

    }
}
