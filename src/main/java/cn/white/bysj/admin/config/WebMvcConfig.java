package cn.white.bysj.admin.config;

import cn.white.bysj.admin.config.interceptor.ApiInterceptor;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import cn.white.bysj.admin.config.interceptor.CommonInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private CommonInterceptor commonInterceptor;

	@Autowired
	private ApiInterceptor apiInterceptor;

	/**
	 * fastJson相关设置
	 */
	private FastJsonConfig getFastJsonConfig() {
		
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 在serializerFeatureList中添加转换规则
		List<SerializerFeature> serializerFeatureList = new ArrayList<SerializerFeature>();
		serializerFeatureList.add(SerializerFeature.PrettyFormat);
		serializerFeatureList.add(SerializerFeature.WriteMapNullValue);
		serializerFeatureList.add(SerializerFeature.WriteNullStringAsEmpty);
		serializerFeatureList.add(SerializerFeature.WriteNullListAsEmpty);
		serializerFeatureList.add(SerializerFeature.DisableCircularReferenceDetect);
		SerializerFeature[] serializerFeatures = serializerFeatureList.toArray(new SerializerFeature[serializerFeatureList.size()]);
		fastJsonConfig.setSerializerFeatures(serializerFeatures);

		return fastJsonConfig;
	}
	
	/**
	 * fastJson相关设置
	 */
	private FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter() {

		FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter4();

		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.parseMediaType("text/html;charset=UTF-8"));
		supportedMediaTypes.add(MediaType.parseMediaType("application/json"));

		fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		fastJsonHttpMessageConverter.setFastJsonConfig(getFastJsonConfig());

		return fastJsonHttpMessageConverter;
	}
	
	/**
	 * 添加fastJsonHttpMessageConverter到converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(fastJsonHttpMessageConverter());
	}

	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		/**
		 * 后台管理员操作拦截器
		 */
		registry.addInterceptor(commonInterceptor).addPathPatterns("/admin/**","/","/index");

		/**前台调用api接口拦截器
		 * 添加拦截路径
		 */
		registry.addInterceptor(apiInterceptor)
				.addPathPatterns("/user/**","/notebook/**","/home/**","/note/**","/home/**",
				"/comment/**","/label/**","/feedback/**")
				.excludePathPatterns(
						"/user/login.do",
						"/user/logout.do","/user/register.do","/user/updataAvatar.do",
				"/user/activate.do","/user/sendCheckNum.do","/user/checkEmailIsExist.do",
				"/user/checkTelephoneCheckNum.do","/user/getWeather.do","/user/getCity.do","/home/findHome.do",
				"/note/uploadFile.do"
				,"/note/findNoteByIdFromShareNote.do","/user/findUserByIdFromShareNote.do","/note/addRead.do","/comment/CommentListByNoteId.do"
				);

		/**
		 *
		 * 放行无须验证的路径
		 */
//		registry.addInterceptor(apiInterceptor).excludePathPatterns("/user/login.do","/user/logout.do","/user/register.do",
//				"/user/activate.do","/user/sendCheckNum.do","/user/checkEmailIsExist.do",
//				"/user/checkTelephoneCheckNum.do","/user/getWeather.do","/user/getCity.do");
		super.addInterceptors(registry);
	}
	
    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new OpenEntityManagerInViewFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
	
	
}
