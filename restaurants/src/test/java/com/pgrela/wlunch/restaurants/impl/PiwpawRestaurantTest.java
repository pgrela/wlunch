package com.pgrela.wlunch.restaurants.impl;

import com.pgrela.wlunch.api.TodayMenu;
import com.pgrela.wlunch.restaurants.utils.Downloader;
import com.pgrela.wlunch.spring.Restaurants;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Restaurants.class})
public class PiwpawRestaurantTest extends AbstractTestNGSpringContextTests {

    @Mock
    private Downloader downloader;

    @InjectMocks
    @Autowired
    private PiwpawRestaurant piwpawRestaurant;

    @BeforeMethod
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(enabled = false)
    @Ignore
    public void shouldFormatMenu() throws IOException {
        //given

        //when
        TodayMenu todayMenu = piwpawRestaurant.getMenu();

        //then
        assertThat(todayMenu.getMenuText()).contains("karkówka");
    }
}
