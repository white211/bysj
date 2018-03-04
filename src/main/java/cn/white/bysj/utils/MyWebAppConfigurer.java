package cn.white.bysj.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Create by @author white
 *
 * @date 2018-01-11 15:00
 */
//进行跨域请求设置
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
