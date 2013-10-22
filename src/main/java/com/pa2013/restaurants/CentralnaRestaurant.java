package com.pa2013.restaurants;

/**
 * @author: pgrela
 */
public class CentralnaRestaurant extends AbstractHtmlRestaurant {
    public static final String URL = "http://www.eurobistrocatering.pl/menu/stolowka-centralna-pw-politechnika.html";

    @Override
    String getUrl() {
        return URL;
    }

    protected String getSelector() {
        return "div.item-page";
    }

    @Override
    protected String stripMenu(String menu) {
        return menu.replaceAll("(?s)^.*Menu.*?[0-9]{2}.*?\n", "");
    }
}
