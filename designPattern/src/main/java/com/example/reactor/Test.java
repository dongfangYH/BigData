package com.example.reactor;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-10-28 15:01
 **/
public class Test {
    public static void main(String[] args) throws InterruptedException {


        Mono.just("a").zipWith(Mono.just(1)).doOnEach(t -> {
            Tuple2<String, Integer> tuple2 = t.get();
            System.out.println(tuple2.getT1());
            System.out.println(tuple2.getT2());
        }).subscribe();


        Flux<String> seq = Flux.just("foot hand", "feet head", "hand arm");
        Mono<List<String>> mono = seq.flatMap(e ->Flux.just(e.split(" "))).distinct().collectList();
        List<String> result = mono.block();
        System.out.println(result);

        Flux.just("3", "1", "a")
                .subscribe(r -> System.out.println("successful convert num : " + Integer.valueOf(r)),
                           ex -> System.out.println("convert num fail, reason : " + ex),
                           () -> System.out.println("done"),
                            sub -> sub.request(10)
                );

        Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + (3 * i));
                    if (i == 10) sink.complete();
                    return state;
                }, (state) -> System.out.println("state:" + state)
        ).subscribe();


        Scheduler scheduler = Schedulers.elastic();
        Disposable disposable = scheduler.schedule(() -> {
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task done");
        });

    }
}
