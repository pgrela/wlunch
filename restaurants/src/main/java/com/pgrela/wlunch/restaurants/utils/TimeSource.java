package com.pgrela.wlunch.restaurants.utils;

import com.pgrela.wlunch.restaurants.menu.MenuTransformer;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class TimeSource {

    public Calendar getTodaysCalendar() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_YEAR, -0);
        return instance;
    }

    public String getTodayDayName() {
        return new SimpleDateFormat("EEEE", MenuTransformer.LOCALE_PL).format(getTodaysCalendar().getTime());
    }
}