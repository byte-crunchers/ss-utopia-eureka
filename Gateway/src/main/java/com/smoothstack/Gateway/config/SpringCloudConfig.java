package com.smoothstack.Gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

		return builder.routes()
				.route(r -> r.path("/login**").uri("lb://AUTH-CLIENT"))
				.route(r -> r.path("/utopia/**").uri("lb://EUREKA-CLIENT"))
//				.route(r -> r.path("/status/**").uri("http://httpbin.org"))
				.build();
	}

}
