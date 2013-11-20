package com.pgrela.wlunch.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.pgrela.wlunch.restaurants.AbstractRestaurant;

@Component
public class TimeSource {
    public TimeSource() {
    }

    public Calendar getTodaysCalendar() {
        return Calendar.getInstance();
    }
    public String getTodayDayName(){
        return new SimpleDateFormat("EEEE", AbstractRestaurant.LOCALE_PL).format(new Date());
    }
}