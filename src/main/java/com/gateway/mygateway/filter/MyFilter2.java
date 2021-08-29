package com.gateway.mygateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class MyFilter2 implements Ordered, GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        List<String> token = queryParams.get("token");
        if(token == null || token.size() == 0) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("none auth".getBytes());
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        }
        System.out.println("check token 2");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
