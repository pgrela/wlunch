package com.pgrela.wlunch.restserver;

import com.pgrela.wlunch.spring.Restaurants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

@SpringBootApplication
@Import(Restaurants.class)
public class Server {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Server.class);
    }

    @Bean
    WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        return new
                WebMvcConfigurerAdapter() {
                    @Override
                    public void addResourceHandlers(ResourceHandlerRegistry registry) {
                        registry.addResourceHandler("/", "index.html")
                                .addResourceLocations("classpath:/META-INF/resources/webjars/wlunch-rest-server/");
                        registry.addResourceHandler("/webjars/**")
                                .addResourceLocations("classpath:/META-INF/resources/webjars/");
                    }
                };
    }
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**");
//            }
//        };
//    }
}
