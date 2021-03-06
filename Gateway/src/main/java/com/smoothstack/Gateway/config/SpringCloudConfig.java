package com.smoothstack.Gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

		return builder.routes().route(r -> r.path("/login").uri("lb://AUTH-CLIENT"))
				.route(r -> r.path("/api/v0.1/accountTypes/**").uri("lb://ACCOUNT-CLIENT"))
				.route(r -> r.path("/api/v0.1/accounts/**")
						.filters(f -> f.rewriteLocationResponseHeader("AS_IN_REQUEST", "Location", null, "https"))
						.uri("lb://ACCOUNT-CLIENT"))
				.route(r -> r.path("/api/v0.1/LoanTypes/**").uri("lb://LOAN-CLIENT"))
				.route(r -> r.path("/api/v0.1/cards/**")
						.filters(f -> f.rewriteLocationResponseHeader("AS_IN_REQUEST", "Location", null, "https"))
						.uri("lb://ACCOUNT-CLIENT"))
				.route(r -> r.path("/api/v0.1/Loans/**")
						.filters(f -> f.rewriteLocationResponseHeader("AS_IN_REQUEST", "Location", null, "https"))
						.uri("lb://LOAN-CLIENT"))
				.route(r -> r.path("/userinfo/**")
						.filters(f -> f.rewriteLocationResponseHeader("AS_IN_REQUEST", "Location", null, "https"))
						.uri("lb://USERS-CLIENT"))
//				.route(r -> r.path("/status/**").uri("http://httpbin.org"))
				.build();
	}

}
