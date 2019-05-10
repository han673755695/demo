package com.example.demo.config;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 解决使用spring boot2.0 jackson配置日期格式化不生效问题
 * 以前是用 WebMvcConfigurerAdapter ，
 * springboot 2.0 建议使用 WebMvcConfigurationSupport 。
 * 但是在添加拦截器并继承 WebMvcConfigurationSupport 
 * 后会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置！
 * 从而导致所有的Date返回都变成时间戳！
 * @author my
 *
 */
@Configuration
public class JacksonConfigurer implements WebMvcConfigurer{
	
	//定义时间格式转换器
	@Bean
	public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
	    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	    converter.setObjectMapper(mapper);
	    return converter;
	}

	//添加转换器
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	    //将我们定义的时间格式转换器添加到转换器列表中,
	    //这样jackson格式化时候但凡遇到Date类型就会转换成我们定义的格式
	    converters.add(jackson2HttpMessageConverter());
	}

}
