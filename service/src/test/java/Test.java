import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by admin on 2016/1/28.
 */
public class Test {
    public static void main(String args[]) {

        // String str = "{\"msg\":\"请求成功\",\"code\":200,\"data\":null}";
        //JSONObject obj = JSONObject.parseObject(str);
        // System.out.println(obj.getString("msg"));
        // System.out.println(obj.getString("code"));
        try {
            String mmm = "{\"msg\":\"è¯·æ±\u0082æ\u0088\u0090å\u008A\u009F\",\"code\":200,\"data\":[{\"typeId\":3593,\"name\":\"å\u00AD\u0090ç±»ç\u009B B\",\"standardTypeId\":\"2002\",\"parentStandardTypeId\":\"200\",\"path\":\"|200|2002|\",\"disabled\":false,\"customerId\":-1,\"brandList\":[],\"specList\":null,\"pathStr\":null,\"parent\":false,\"physical\":true,\"torder\":1},{\"typeId\":3592,\"name\":\"å\u00AD\u0090ç±»ç\u009B A\",\"standardTypeId\":\"2001\",\"parentStandardTypeId\":\"200\",\"path\":\"|200|2001|\",\"disabled\":false,\"customerId\":-1,\"brandList\":[],\"specList\":null,\"pathStr\":null,\"parent\":false,\"physical\":true,\"torder\":2}]}";
//            String mmm = "{\"msg\":\"è¯·æ±\u0082æ\u0088\u0090å\u008A\u009F\",\"code\":200,\"data\":[{\"typeId\":3593,\"name\":\"å\u00AD\u0090ç±»ç\u009B®B\",\"standardTypeId\":\"2002\",\"parentStandardTypeId\":\"200\",\"path\":\"|200|2002|\",\"disabled\":false,\"customerId\":-1,\"brandList\":[],\"specList\":null,\"pathStr\":null,\"parent\":false,\"physical\":true,\"torder\":1},{\"typeId\":3592,\"name\":\"å\u00AD\u0090ç±»ç\u009B®A\",\"standardTypeId\":\"2001\",\"parentStandardTypeId\":\"200\",\"path\":\"|200|2001|\",\"disabled\":false,\"customerId\":-1,\"brandList\":[],\"specList\":null,\"pathStr\":null,\"parent\":false,\"physical\":true,\"torder\":2}]}";
            String newStr =new String(mmm.getBytes("ISO-8859-1"),"UTF-8");
            System.out.println(newStr);
            JSONObject json = JSONObject.parseObject(newStr);
            String str = json.getString("data");
            JSONArray arr = JSONObject.parseArray(str);
            for (int i = 0; i < arr.size(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                System.out.println(obj.getString("name"));

            }
            System.out.println(json.getString("msg"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }


}
