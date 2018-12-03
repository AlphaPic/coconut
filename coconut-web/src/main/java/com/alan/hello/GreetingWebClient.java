package com.alan.hello;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @Author alan
 * @Date 2018/12/3
 */
public class GreetingWebClient {
	private WebClient client = WebClient.create("http://localhost:8080");

	private Mono<ClientResponse> result = client
		.get()
		.uri("/hello")
		.accept(MediaType.TEXT_PLAIN)
		.exchange();

	private Mono<ClientResponse> result1 = client
		.get()
		.uri("/greet")
		.accept(MediaType.TEXT_PLAIN)
		.exchange();

	public String getResult() {
		return ">> result = " + result.flatMap(res -> res.bodyToMono(String.class)).block();
	}
}
