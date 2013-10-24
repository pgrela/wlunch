package com.pgrela.wlunch.restaurants;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.testng.annotations.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.pgrela.wlunch.common.Restaurant;
import com.pgrela.wlunch.utils.Downloader;

public class CentralnaRestaurantTest {
    @Test
    public void shouldFormatMenu() throws IOException {
        //given
        Downloader downloaderMock = mock(Downloader.class);
        Restaurant centralnaRestaurant = new CentralnaRestaurant(downloaderMock);
        String webpage = Resources.toString(Resources.getResource("pages/centralna.html"), Charsets.UTF_8);
        when(downloaderMock.downloadElement(anyString(), anyString())).thenReturn(webpage);

        //when
        String menu = centralnaRestaurant.getMenu();

        //then
        assertThat(menu).contains("karkówka");
    }

    @Test
    public void shouldWarnIfOtherDateThanToday() throws IOException {
        //given
        Downloader downloaderMock = mock(Downloader.class);
        CentralnaRestaurant centralnaRestaurant = new CentralnaRestaurant(downloaderMock);
        String text = Jsoup.parse(
                Resources.toString(Resources.getResource("pages/centralna.html"), Charsets.UTF_8)).select(
                centralnaRestaurant.getSelector()).text();
        when(downloaderMock.downloadElement(anyString(), anyString())).thenReturn(text);

        //when
        String menu = centralnaRestaurant.getMenu();

        //then
        assertThat(menu).contains("not up to date");
    }

    @Test
    public void shouldExtractDates() {
        //given
        String text = "22-10-2012";
        CentralnaRestaurant centralnaRestaurant = new CentralnaRestaurant(new Downloader());

        //when
        List<Calendar> dates = centralnaRestaurant.getPossibleDates(text);

        //then
        assertThat(dates).isNotEmpty();
    }
}
