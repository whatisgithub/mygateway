package com.gateway.mygateway.flux;

import org.bouncycastle.asn1.x509.sigi.PersonalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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

}
