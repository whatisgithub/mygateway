package com.gateway.mygateway.router;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customeRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes().route("gateway-route", f -> f.path("/gateway-route/**")
                        .filters(filter -> filter.stripPrefix(1))
                        .uri("http://httpbin.org"))
                .build();
    }

    /**
     * 注意直接使用webflux后不会走gateway 全局过滤器了
     * 这个只是一个普通webflux应用，不会转发请求到后端service
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        RouterFunction<ServerResponse> ok = RouterFunctions.route(RequestPredicates.path("/003"),
                req -> ServerResponse.ok().body(BodyInserters.fromValue("name")));
        return ok;
    }
}
