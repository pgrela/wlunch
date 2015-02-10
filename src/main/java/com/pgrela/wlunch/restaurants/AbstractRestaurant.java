package com.pgrela.wlunch.restaurants;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.pgrela.wlunch.common.Restaurant;

public abstract class AbstractRestaurant implements Restaurant {

    public static final Locale LOCALE_PL = Locale.forLanguageTag("pl-PL");

    abstract String getUrl();

    protected List<Calendar> getPossibleDates(String text) {
        return Collections.emptyList();
    }

    @Override
    public boolean isOpened(){
        return true;
    }
}