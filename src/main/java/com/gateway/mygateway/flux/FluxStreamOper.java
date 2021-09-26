package com.gateway.mygateway.flux;

import reactor.core.Disposable;
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


        System.out.println("==================1111");

        Flux<Mono<Integer>> monoFlux = Flux.range(3, 7).map(ele -> {
            System.out.println("1111111111111111 hello=" + ele);
            if (1 == 1) throw new RuntimeException("111");
            return Mono.just(4);
        }).onErrorReturn(Exception.class, Mono.just(1));

        System.out.println(monoFlux.next());
    }
}
