package com.alan.hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @Author alan
 * @Date 2018/12/3
 */
@Configuration
public class GreetingRouter {
	@Bean
	public RouterFunction<ServerResponse> route(GreetingHandler handler){
		return RouterFunctions
			.route(RequestPredicates
						.GET("/hello")
						.and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::hello);
	}

	@Bean
	public RouterFunction<ServerResponse> route1(GreetingHandler handler){
		return RouterFunctions
			.route(RequestPredicates
				.GET("/greet")
				.and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::greet);
	}
}
