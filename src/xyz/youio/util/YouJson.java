package xyz.youio.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin@earthvip.xyz on 2017/1/24.
 * dao层生成数据转化为json
 */
public class YouJson {
    public static String toJson(List in) {
        String result = "";
        if (!(in.get(0) instanceof Boolean)) {
            result = result + "[";
            int inSize = in.size();
            int j=0;
            for (Object temp:in){
                result = result + "{";
                Map map= (Map) temp;
                int size = map.size();
                int i=0;
                for (Object key:map.keySet()){
                    //字符串替换"符号
                    String s = ("" + map.get(key)).replaceAll("\\\"", "\\\\\"").replaceAll("\\/", "\\\\/");
                    result = result +"\""+key+"\":\""+s;
                    i++;
                    if(i!=size){
                        result = result +"\",";
                    }else{
                        result = result +"\"}";
                    }
                }
                j++;
                if(j!=inSize){
                    result = result +",";
                }
            }
            result = result + "]";
        } else {
            result = ""+in.get(0);
        }
        return result;
    }
}
