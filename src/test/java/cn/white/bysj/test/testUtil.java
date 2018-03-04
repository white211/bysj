package cn.white.bysj.test;

import cn.white.bysj.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by @author white
 *
 * @date 2018-01-12 16:49
 */
public class testUtil {
    public static void main(String[] args) {
        User user = new User();
        user.setCn_user_name("white");
        List<String> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)){
            System.out.println("集合为空");
        }else{
            System.out.println("集合不为空");
        }
        if (StringUtils.isEmpty(user.getCn_user_name())){
            System.out.println("用户名为空");
        }else{
            System.out.println("用户名不为空");
        }
    }
}
