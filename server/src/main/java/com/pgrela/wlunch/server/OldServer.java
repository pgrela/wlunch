package com.pgrela.wlunch.server;

import com.pgrela.wlunch.spring.Restaurants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.io.IOException;

@SpringBootApplication
@Import(Restaurants.class)
public class OldServer {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(OldServer.class);
    }
}
