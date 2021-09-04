package com.gateway.mygateway.flux;

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
}
