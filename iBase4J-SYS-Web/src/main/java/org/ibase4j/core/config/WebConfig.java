package org.ibase4j.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import top.ibase4j.core.config.WebMvcConfig;
import top.ibase4j.core.interceptor.EventInterceptor;

/**
 * @author ShenHuaJie
 * @since 2018年4月21日 下午3:28:48
 */
@Configuration
@ComponentScan("org.ibase4j.web")
public class WebConfig extends WebMvcConfig {
	@Bean
	public EventInterceptor eventInterceptor() {
		return new EventInterceptor();
	}
}
