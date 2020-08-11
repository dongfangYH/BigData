package com.example.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class WebfluxApplication {

    @Bean
    RouterFunction<ServerResponse> home() {
        return route(GET("/"), request -> ok().body(fromValue("homePage")));
    }

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

}
