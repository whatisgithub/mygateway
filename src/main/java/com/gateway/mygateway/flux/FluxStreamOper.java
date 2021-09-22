package com.gateway.mygateway.flux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author zhu.q
 */
public class FluxStreamOper {
    public static void main(String[] args) {
        Flux.range(1, 10).map(ele -> ele * ele).subscribe(System.out::println);
        //一个一个的处理
        Flux.range(1, 10).concatMap(ele -> {
            System.out.println("ele====" + ele);
            return Mono.just(ele+ele);
        }).subscribe(System.out::println);

        System.out.println("==========");
        //并行处理
        Flux.range(1, 10).flatMap(ele -> {
//            System.out.println("ele === flatMap = "+ ele);
            return Mono.just(ele);
        }).next().subscribe(System.out::println);

        System.out.println("分割线=====================");
        Flux.range(1, 10).concatMap(ele -> Mono.just(ele)).next().subscribe(e -> System.out.println(e));
    }
}
