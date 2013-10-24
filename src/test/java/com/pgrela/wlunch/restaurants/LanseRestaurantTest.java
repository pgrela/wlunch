package com.pgrela.wlunch.restaurants;

import static org.fest.assertions.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.testng.annotations.Test;

public class LanseRestaurantTest {
    @Test
    public void testGetPossibleDates() throws Exception {
        LanseRestaurant lanseRestaurant = new LanseRestaurant();

        List<Calendar> dates = lanseRestaurant.getPossibleDates("18-10-2013.");
        List<String> datesStrings = new ArrayList<String>();
        for (Calendar date : dates) {
            datesStrings.add(new SimpleDateFormat("dd-MM-yyyy").format(date.getTime()));
        }

        assertThat(datesStrings).contains("17-10-2013");
    }
}
