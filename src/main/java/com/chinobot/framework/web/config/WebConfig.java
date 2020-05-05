package com.chinobot.framework.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
public class WebConfig extends WebMvcConfigurationSupport{
	
	@Autowired
	private TokenInterceptor tokenInterceptor;
	
	/**
	 * 自定义拦截器
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor).addPathPatterns("/**")
				.excludePathPatterns("/api/app/**","/api/home/**", "/api/login","/api/setting", "/api/info/info/kmlUpload","/api/file/upload","/api/file/ioimage","/api/kafka/send/**", "/api/basedata","/error", "/swagger-resources",
						"/api/bot/resource/live-broadcast/getSendUrl","/api/bot/resource/live-broadcast/getPlayUrl", "/api/bot/prospect/flight-work/postCollectData","/api/bot/prospect/flight-work/testWarningPostProBase","/api/bot/prospect/flight-work/testWarningPostPro","/api/bot/prospect/flight-work/testWarningPost", "/*.html" ,"/webjars/**");
		super.addInterceptors(registry);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("doc.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
		super.addResourceHandlers(registry);
	}

}
