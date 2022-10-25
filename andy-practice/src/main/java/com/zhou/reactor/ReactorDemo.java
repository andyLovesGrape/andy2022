package com.zhou.reactor;

import com.google.common.collect.Lists;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author zhouyuanke
 * @date 2022/10/24 21:01
 */
public class ReactorDemo {

    private static void fluxCreate() {
        Flux.just(1, 2, 3).subscribe(System.out::println);
        Flux.fromArray(new Integer[]{1, 2, 3}).subscribe(System.out::println);
        Flux.fromIterable(Lists.newArrayList(1, 2, 3)).subscribe(System.out::println);
        Flux.fromStream(Stream.of(1, 2, 3)).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        try {
            Flux.error(new RuntimeException("test")).subscribe(System.out::println);
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
        }
        Flux.never().subscribe(System.out::println);
        Flux.range(1, 3).subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
    }

    private static void fluxCreate2() {
        Flux.generate(sink -> {
            sink.next("hello world");
            sink.complete();
        }).subscribe(System.out::println);
        Flux.create(sink -> {
            for (int i = 0; i < 3; i++) {
                sink.next(i);
            }
        }).subscribe(System.out::println);
    }

    private static void monoCreate() {
        Mono.just("hello world").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("hello world")).subscribe(System.out::println);
        Mono.from(Mono.just("hello world")).subscribe(System.out::println);
        Mono.fromDirect(Mono.just("hello world")).subscribe(System.out::println);
        Mono.fromCallable((Callable<Object>) () -> "hello world").subscribe(System.out::println);
        Mono.fromCompletionStage(CompletableFuture.supplyAsync(() -> "hello world")).subscribe(System.out::println);
        Mono.fromFuture(CompletableFuture.completedFuture("hello world")).subscribe(System.out::println);
        Mono.fromSupplier((Supplier<Object>) () -> "hello world").subscribe(System.out::println);
        Mono.create(sink -> sink.success("hello world")).subscribe(System.out::println);
    }

    private static void fluxOperation() {
        Flux.range(1, 10).buffer(5).subscribe(System.out::println);
        Flux.range(1, 10).filter(it -> it % 2 == 0).subscribe(System.out::println);
        Flux.just(1, 2).zipWith(Flux.just(3, 4),
                (BiFunction<Integer, Integer, Object>) (integer, integer2) -> integer + integer2)
                .subscribe(System.out::println);
        Flux.range(1, 5).take(3).subscribe(System.out::println);
        Flux.range(1, 5).reduce((integer, integer2) -> integer + integer2).subscribe(System.out::println);
        Flux.range(1, 3).mergeWith(Flux.range(5, 3)).subscribe(System.out::println);
        Flux.just(5, 10).flatMap((Function<Integer, Publisher<?>>) integer -> Flux.interval(Duration.ofMillis(100)).take(integer))
                .subscribe(System.out::println);
        Flux.just(5, 10).concatMap((Function<Integer, Publisher<?>>) integer -> Flux.interval(Duration.ofMillis(100)).take(integer))
                .subscribe(System.out::println);
        Flux.combineLatest((Function<Object[], Object>) objects -> {
            System.out.println("combine");
            return Lists.newArrayList(objects);
        }, Flux.just(1, 5), Flux.range(5, 3)).subscribe(System.out::println);
    }

    public static void main(String[] args) {
        fluxOperation();
    }
}