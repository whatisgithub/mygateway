package com.gateway.mygateway.flux;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FluxController {

    /**
     * sse server send event 服务端单向通知客户端
     * 有区别于webSocket以及长短轮询
     * @return
     */
    @RequestMapping(value = "/sse", produces = "text/event-stream;charset=utf-8")
    public Object testStream() {
        return "ss";
    }

    /**
     * 阻塞式编程
     * @return
     */
    @RequestMapping("/nomalGet")
    public String get(){
        System.out.println("---1");
        String result = "xxxxx";
        System.out.println("----2");
        return result;
    }

    @RequestMapping("/monoGet")
    public Mono<String> monoGet(){
        System.out.println("---1");
        Mono<String> mono = Mono.create(sink -> {
            try {
                Thread.sleep(3000);
                System.out.println("---2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("----3");
        return mono;
    }




}
