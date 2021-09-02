package com.gateway.mygateway.flux;

import reactor.core.publisher.Flux;

import java.util.stream.Stream;

public class PlayFlux {
    public static void main(String[] args) {
        //flux 0-n个数据的集合，只不过这个集合是个响应式的集合，即返回给客户端的时候后续可以源源不断的给客户端，不需要客户端重新发送请求
        //区别于List 基本上后台业务线程组装完了以后才能给客户端，后续客户端想要数据只能重新请求
        Flux<String> flux = Flux.fromStream(Stream.of("hi", "hello"));
        flux.subscribe(System.out::println);
        //已知集合元素大小使用just
        Flux<String> flux2 = Flux.just(new String[]{"1", "2"});
        flux2.subscribe(System.out::println);

        Flux.generate(sink -> {
            sink.next("xx");
            //不能调用两次
//            sink.next("oo");
            sink.complete();
        }).subscribe(System.out::println);


        //异步
        Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        }).subscribe(System.out::println);
    }
}
