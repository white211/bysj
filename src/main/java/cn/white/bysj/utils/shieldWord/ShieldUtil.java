package cn.white.bysj.utils.shieldWord;

import java.util.HashSet;
import java.util.Set;

/**
 * Create by @author white
 *
 * @date 2018-04-19 16:22
 */
public class ShieldUtil {

    /**
     * 获取替换后的字符串
     * @param str
     * @param set
     * @return 替换后的字符串
     */
    public static String getReplaceStr(String str, Set<String> set){
        ShieldFilter filter = new ShieldFilter(set);
        return filter.replaceShield(str,2,"*");
    }


    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("法轮功");
        set.add("fuck");
        System.out.println(getReplaceStr("我要fuck传说中的法轮功",set));
    }



}


