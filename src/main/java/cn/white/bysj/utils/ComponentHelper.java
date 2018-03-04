package cn.white.bysj.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2017-12-27 22:06
 */
public class ComponentHelper {

    //将前台数据request转化成Map
    public static Map<String, Object> requestToMap(HttpServletRequest request) {
        Enumeration names = request.getParameterNames();
        Map map = new HashMap();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            System.out.print(name+" ");
            String[] s = request.getParameterValues(name);
            if (s != null) {
                if (s.length > 1) {
                    map.put(name, s);
                } else {
                    map.put(name, s[0]);
                }
            }
        }
        System.out.println();
        return map;
    }
}
