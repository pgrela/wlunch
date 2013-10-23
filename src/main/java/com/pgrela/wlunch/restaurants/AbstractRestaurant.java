package com.pgrela.wlunch.restaurants;

import java.util.Locale;

import com.pgrela.wlunch.common.Restaurant;

public abstract class AbstractRestaurant implements Restaurant {

    public static final Locale LOCALE_PL = Locale.forLanguageTag("pl-PL");

    abstract String getUrl();
}