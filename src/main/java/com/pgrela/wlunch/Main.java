package com.pgrela.wlunch;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);
        System.out.println(context.getBean(Menu.class).getMenuForToday());
    }
}
