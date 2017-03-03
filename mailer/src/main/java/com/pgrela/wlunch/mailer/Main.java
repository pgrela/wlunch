package com.pgrela.wlunch.mailer;

import com.pgrela.wlunch.spring.Restaurants;
import com.pgrela.wlunch.hunger.ErrandBoy;
import com.pgrela.wlunch.restaurants.utils.TimeSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {

    private TimeSource timeSource;

    public static void main(String[] args) throws IOException {

        ApplicationContext context = new AnnotationConfigApplicationContext(Restaurants.class);
        ErrandBoy errandBoy = context.getBean(ErrandBoy.class);
        MenuMailer menuMailer = context.getBean(MenuMailer.class);


        String menuForToday = errandBoy.getMenuForToday();
        //menuMailer.send(menuForToday);

        System.out.println(menuForToday);
    }
}
