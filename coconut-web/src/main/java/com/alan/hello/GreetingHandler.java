package com.alan.hello;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @Author alan
 * @Date 2018/12/3
 */
@Component
public class GreetingHandler {
	public Mono<ServerResponse> hello(ServerRequest request){
		return ServerResponse.ok()
			.contentType(MediaType.TEXT_PLAIN)
			.body(BodyInserters.fromObject("Hello,Spring!"));
	}

	public Mono<ServerResponse> greet(ServerRequest request){
		return ServerResponse.ok()
			.contentType(MediaType.TEXT_PLAIN)
			.body(BodyInserters.fromObject("Hello,Micheal!"));
	}
}
