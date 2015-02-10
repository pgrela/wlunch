package com.pgrela.wlunch.restaurants;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pgrela.wlunch.SpringContext;

@ContextConfiguration(classes = {SpringContext.class})
public class LanseRestaurantTest extends AbstractTestNGSpringContextTests {
    @Autowired
    LanseRestaurant lanseRestaurant;

    @Test
    public void testGetPossibleDates() throws Exception {
        List<Calendar> dates = lanseRestaurant.getPossibleDates("18.10.2013");
        List<String> datesStrings = new ArrayList<String>();
        for (Calendar date : dates) {
            datesStrings.add(new SimpleDateFormat("dd-MM-yyyy").format(date.getTime()));
        }

        assertThat(datesStrings).contains("17-10-2013");
    }

    @Test
    public void shouldFormatMenu() throws IOException {
        //given

        //when
        Menu menu = lanseRestaurant.getMenu();

        //then
        assertThat(menu.toString()).contains("karkówka");
    }
}
