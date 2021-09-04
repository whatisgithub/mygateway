package com.gateway.mygateway.fluxroute;

import com.gateway.mygateway.flux.Person;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class FluxHandler {

    public Mono<ServerResponse> monoHandler(ServerRequest request){
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("hello flux"));
    }

    public Mono<ServerResponse> jsonHandler(ServerRequest request){

        Person per = new Person();
        per.setName("111");
        per.setAge("222");
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(per));
    }

    public Mono<ServerResponse> paramHandler(ServerRequest request){

        Optional<String> id = request.queryParam("id");
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(id));
    }

    public Mono<ServerResponse> pathVarableHandler(ServerRequest request){

        String id = request.pathVariable("id");
        String name = request.pathVariable("name");
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(id + ", " + name));


//        return ServerResponse.status(666).build();
    }
}
