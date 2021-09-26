package com.gateway.mygateway.flux;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 注解式编程
 * 函数式编程
 * 注意系统启动已经采用netty了，而不是采用tomcat
 * 要想采用tomcat只需要引入spring-starter-web 包就可以了
 */
@RestController
public class PersonController {


    @Autowired
    PersonService personService;

    @GetMapping("/monoPerson")
    public Mono<Object> getPerson() {
        Mono<Object> result = Mono.create(sink ->
                        sink.success(personService.getPerson())
                ).doOnSubscribe(sub ->
                        System.out.println("sub---" + sub)
                ).doOnNext(data -> System.out.println("onnext--" + data))
                .doOnSuccess(onSuccess ->
                        System.out.println("success--" + onSuccess)
                );
        return result;
    }

    /**
     * 区别于springMVC request  session获取方式有所不同
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/request-info")
    public Mono<Object> getRequest(ServerHttpRequest request, WebSession session) {
        System.out.println(request);
        if(session.getAttribute("zhangsan") == null) {
            session.getAttributes().put("zhangsan", "1");
            System.out.println("set session ======");
        }
        return Mono.just("hello-----" + ", " + request.getQueryParams());
    }

    @GetMapping(value = "/sse2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sseTest() {
        Flux<String> flux = Flux.fromStream(IntStream.range(1, 10).mapToObj(i -> {
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i+"---";
        })).doOnSubscribe(sub -> {
            System.out.println("sub: " + sub);
        }).doOnComplete(() -> {
            System.out.println("complete method");
        }).doOnNext(data -> {
            System.out.println("onnext=" + data);
        });
        return flux;
    }

    public static void main(String[] args) {
        Flux.fromStream(IntStream.range(1, 10).mapToObj(i -> i+"")).log().subscribe(ele -> {
            System.out.println(ele);
        });

        System.out.println("====================");


        Flux.just("test1", "test2", "test3").log().subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("啥时候调用我---onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                System.out.println("啥时候调用我---onNext=" + s);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("啥时候调用我---onError");
            }

            @Override
            public void onComplete() {
                System.out.println("啥时候调用我---onComplete");
            }
        });


        System.out.println("==============");
        Flux.just("f1", "f2", "f3").log().doOnNext(e -> System.out.println(e)).subscribe();
    }
}
