package com.pgrela.wlunch.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"com.pgrela.wlunch.restaurants", "com.pgrela.wlunch.hunger"})
@Configuration
public class Restaurants {
}
