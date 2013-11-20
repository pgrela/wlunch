package com.pgrela.wlunch;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.pgrela.wlunch.utils.MenuMailer;
import com.pgrela.wlunch.utils.TimeSource;

public class Main {

    private TimeSource timeSource;

    public static void main(String[] args) throws IOException {

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);
        Menu menu = context.getBean(Menu.class);
        MenuMailer menuMailer = context.getBean(MenuMailer.class);


        String menuForToday = menu.getMenuForToday();
        menuMailer.send(menuForToday);

        System.out.println(menuForToday);
    }
}
