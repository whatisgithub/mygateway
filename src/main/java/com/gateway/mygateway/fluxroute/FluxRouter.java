package com.gateway.mygateway.fluxroute;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class FluxRouter {

    /**
     * router = Controller + @RequestMapping
     * 一个router对应一个handler
     * 每个handler代表一个处理器 或者说Controller里边的方法
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> routerFlux(FluxHandler handler) {
        return RouterFunctions.route(
                RequestPredicates.path("/001")
                        .and(RequestPredicates.method(HttpMethod.GET))
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                request -> ServerResponse.ok().body(BodyInserters.fromValue("iambody"))
                )

                .andRoute(RequestPredicates.path("/002"), handler::monoHandler)
                .andRoute(RequestPredicates.path("/003"), handler::jsonHandler)
                .andRoute(RequestPredicates.path("/004"), handler::paramHandler)
                .andRoute(RequestPredicates.path("/005/{id}/{name}"), handler::pathVarableHandler)
                ;
    }
}
