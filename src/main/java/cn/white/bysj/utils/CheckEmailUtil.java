package cn.white.bysj.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by @author white
 *
 * @date 2018-01-03 19:14
 */
public class CheckEmailUtil {

    public static boolean checkEmail(String email){
        String pattern = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(email);
        if(!m.find()){
            System.out.println("邮箱验证错误");
          return true;
        }
        System.out.println("邮箱验证正确");
        return false;
    }

    public static void main(String[] args) {
       checkEmail("942364283@2131.com");
    }

}
