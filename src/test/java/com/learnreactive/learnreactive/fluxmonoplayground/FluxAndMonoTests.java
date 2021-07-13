package com.learnreactive.learnreactive.fluxmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTests {

    @Test
    public void test_flux(){
       Flux<String> stringFlux = Flux.just("Spring","Spring Boot", "Reactive Spring")
               //.concatWith(Flux.error(new RuntimeException("Exception Occurred"))) // Comment and see the logs it gives OnComplete method
               //.concatWith(Flux.just("After Error")) // Once error emitted nothing is sent back
               .log();
       //stringFlux.subscribe(System.out :: println);
       stringFlux.subscribe(System.out :: println,
               (e) -> System.out.println(e),
               ()-> System.out.println("Completed")); // Commenting the concatWith error
    }

    @Test
    public void test_fluxElements_withoutError(){
        Flux<String> stringFlux = Flux.just("Spring","Spring Boot", "Reactive Spring")
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();

    }

    @Test
    public void test_fluxElements_withError(){
        Flux<String> stringFlux = Flux.just("Spring","Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                //.expectError(RuntimeException.class) // Tests the type of exception
                .expectErrorMessage("Exception Occurred")// Test the error message with error message
                .verify();

    }

    @Test
    public void test_fluxElements_withError1(){
        Flux<String> stringFlux = Flux.just("Spring","Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring" ) // Assert in a single line the list
                .expectErrorMessage("Exception Occurred")// Test the error message with error message
                .verify();

    }

    @Test
    public void test_fluxElements_count(){
        Flux<String> stringFlux = Flux.just("Spring","Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("Exception Occurred")// Test the error message with error message
                .verify();

    }

    // Mono tests
    @Test
    public void test_mono(){
        Mono<String> stringMono = Mono.just("Spring")
                        .log();
        StepVerifier.create(stringMono)
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void test_mono_error(){
        StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred")).log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
