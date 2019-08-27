package com.cdk8s.tkey.client.rest.config;


import com.cdk8s.tkey.client.rest.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Bean
	public LoginInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor())
			.addPathPatterns("/user")
			.excludePathPatterns("/", "/error", "/logoutSuccess", "login/**", "/logout/**", "/css/**", "/js/**", "/fonts/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("*")
			.allowedHeaders("*")
			.allowCredentials(true)
			.maxAge(3600L);
	}


}
