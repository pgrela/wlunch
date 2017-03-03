package com.pgrela.wlunch.restaurants.impl;

import com.pgrela.wlunch.api.TodayMenu;
import com.pgrela.wlunch.spring.Restaurants;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(classes = {Restaurants.class})
public class LanseRestaurantTest extends AbstractTestNGSpringContextTests {

    @Autowired
    LanseRestaurant lanseRestaurant;

    @Test(enabled = false)
    @Ignore
    public void shouldFormatMenu() throws IOException {
        //given

        //when
        TodayMenu todayMenu = lanseRestaurant.getMenu();

        //then
        assertThat(todayMenu.getMenuText()).contains("karkówka");
    }
}
