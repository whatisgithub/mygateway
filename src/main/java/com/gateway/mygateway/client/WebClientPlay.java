package com.gateway.mygateway.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author zhu.q
 */
public class WebClientPlay {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 简单用法
         */
        Mono<String> respMono = WebClient.create()
                .get()
                .uri("http://httpbin.org/base64/{str}", "sdlfjsldfsf")
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10));

        respMono.subscribe(body -> {
                    System.out.println(body + "==============");
                }, err -> {
                    System.out.println(err.toString() + "=================err");
                });

        respMono.block();


        /**
         * build webClient
         */
        WebClient webClient = WebClient.builder()
                .baseUrl("http://httpbin.org")
                .defaultHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)")
                .defaultCookie("ACCESS_TOKEN", "test_token")
                .build();

        Mono<String> mono = webClient.get().uri("/base64/helloworld").retrieve().bodyToMono(String.class);
        System.out.println(mono.block());


    }

}
