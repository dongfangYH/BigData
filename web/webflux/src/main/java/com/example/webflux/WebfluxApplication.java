package com.example.webflux;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.Charset;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class WebfluxApplication {

    @Value("${server.port}")
    private String serverPort;

    @Value("${target.host}")
    private String host;

    @Resource
    private Handler handler;


    @Bean("handler")
    Handler downloadHandler(){
        return new Handler();
    }

    @Bean
    RouterFunction<ServerResponse> home() {
        return route().GET("/", request -> ok().body(fromValue("server port: " + serverPort )))
                      .GET("/download", accept(MediaType.ALL), handler::handleDownload)
                      .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

    private class Handler {

        public Mono<ServerResponse> handleDownload(ServerRequest request) {
                String secret = "google.com";
                long exp = (System.currentTimeMillis()/1000) + 300;
                String path = "/static/30c1cf9801d2402dbf309c242837b41d.apk";
                String encodeStr = Base64Utils.encodeToUrlSafeString(
                        DigestUtils.md5Digest((secret + path + exp).getBytes(Charset.forName("UTF-8")))
                );

                String content = "<a href=http://"+host+"/static/30c1cf9801d2402dbf309c242837b41d.apk?st="+encodeStr+"&e="+exp+">WeComics.apk</a>";
                return ok().contentType(MediaType.TEXT_HTML).body(fromValue(content));
        }
    }

}
