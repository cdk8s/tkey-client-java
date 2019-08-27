package com.cdk8s.tkey.client.rest;

import com.cdk8s.tkey.client.rest.service.TkeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass({TkeyService.class})
@EnableConfigurationProperties(TkeyProperties.class)
public class TkeyAutoConfiguration {

	@Autowired
	private TkeyProperties tkeyProperties;


	@Bean
	@ConditionalOnMissingBean(TkeyService.class)
	public TkeyService httpClient() {
		return new TkeyService();
	}
}
