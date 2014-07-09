package com.pgrela.wlunch.restaurants;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.pgrela.wlunch.SpringContext;
import com.pgrela.wlunch.utils.Downloader;

@ContextConfiguration(classes = {SpringContext.class})
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
        Menu menu = centralnaRestaurant.getMenu();

        //then
        assertThat(menu.toString()).contains("karkówka");
    }

    @Test
    public void shouldWarnIfOtherDateThanToday() throws IOException {
        //given
        String text = Jsoup.parse(
                Resources.toString(Resources.getResource("pages/centralna.html"), Charsets.UTF_8)).select(
                centralnaRestaurant.getSelector()).text();
        when(downloader.downloadElement(anyString(), anyString())).thenReturn(text);

        //when
        Menu menu = centralnaRestaurant.getMenu();

        //then
        assertThat(menu.toString()).contains("not up to date");
    }

    @Test
    public void shouldExtractDates() {
        //given
        String text = "22-10-2012";

        //when
        List<Calendar> dates = centralnaRestaurant.getPossibleDates(text);

        //then
        assertThat(dates).isNotEmpty();
    }
}
