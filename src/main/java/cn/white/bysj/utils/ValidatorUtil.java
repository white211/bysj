package cn.white.bysj.utils;

import org.thymeleaf.util.ObjectUtils;

import java.sql.Array;
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

    public static String toString(Object object){
         return object == null  ? "null" : object.toString();
    }



    public static void main(String[] args) {
        int[] arr = {1,2,3,4};
        System.out.println(toString("text"));
        System.out.println(toString(123));


//        Map<String,Object> map = new HashMap<>();
//        List<String> list = new ArrayList<>();
//
//        list.add("password");
//        list.add("account");
//        list.add("email");
//        map.put("password",123456);
////        map.put("account","18814142741");
//
//        System.out.println(validator(map,list));
//
    }
}
