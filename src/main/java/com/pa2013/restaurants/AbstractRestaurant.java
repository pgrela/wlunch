package com.pa2013.restaurants;

import java.util.Locale;

import com.pa2013.Restaurant;

public abstract class AbstractRestaurant implements Restaurant {

    public static final Locale LOCALE_PL = Locale.forLanguageTag("pl-PL");
    private String webpageUrl;
    private String name;

    public void setWebpageUrl(String webpageUrl) {
        this.webpageUrl = webpageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    String getUrl() {
        return webpageUrl;
    }
}