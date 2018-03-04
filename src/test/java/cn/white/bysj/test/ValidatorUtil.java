package cn.white.bysj.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-01-13 23:31
 */
public class ValidatorUtil {

     static List<String> result = null;

    public static List<String> validator(Map<String,Object> map,List<String> list){
        result = new ArrayList<>();
       for (String key:list){
           System.out.println("要求输入参数"+key);
               if (!map.containsKey(key)){
                   result.add(key);
               }
       }
       return result;
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();

        list.add("password");
        list.add("account");
        list.add("email");
        map.put("password",123456);
//        map.put("account","18814142741");

        System.out.println(validator(map,list));
    }
}
