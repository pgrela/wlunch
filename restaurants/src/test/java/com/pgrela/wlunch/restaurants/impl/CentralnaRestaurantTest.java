package com.pgrela.wlunch.restaurants.impl;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.pgrela.wlunch.api.TodayMenu;
import com.pgrela.wlunch.restaurants.utils.Downloader;
import com.pgrela.wlunch.spring.Restaurants;
import org.jsoup.Jsoup;
import org.junit.Ignore;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {Restaurants.class})
public class CentralnaRestaurantTest extends AbstractTestNGSpringContextTests {
    @Mock
    private Downloader downloader;

    @InjectMocks
    @Autowired
    private CentralnaRestaurant centralnaRestaurant;

    @BeforeMethod
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFormatMenu() throws IOException {
        //given
        String webpage = Resources.toString(Resources.getResource("pages/centralna.html"), Charsets.UTF_8);
        when(downloader.downloadElement(anyString(), anyString())).thenReturn(webpage);

        //when
        TodayMenu todayMenu = centralnaRestaurant.getMenu();

        //then
        assertThat(todayMenu.getMenuText()).contains("karkówka");
    }

    @Test(enabled = false)
    @Ignore
    public void shouldWarnIfOtherDateThanToday() throws IOException {
        //given
        String text = Jsoup.parse(
                Resources.toString(Resources.getResource("pages/centralna.html"), Charsets.UTF_8)).select(
                "div.item-page").text();
        when(downloader.downloadElement(anyString(), anyString())).thenReturn(text);

        //when
        TodayMenu todayMenu = centralnaRestaurant.getMenu();

        //then
        assertThat(todayMenu.getMenuText()).contains("not up to date");
    }

}
