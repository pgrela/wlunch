package com.pa2013;

import java.io.IOException;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Menu {

    public static final String PIERROGERIA_URL = "http://www.pierrogeria.eu/";
    public static final String LANSE_URL = "http://restauracja.lanse.pl/danie-dnia";
    public static final Locale LOCALE_PL = Locale.forLanguageTag("pl-PL");

    public static void main(String[] args) throws IOException {
        System.out.println(new Menu().getMenuForToday());
    }

    public String getMenuForToday() throws IOException {
        MenuBuilder menuBuilder = new MenuBuilder();

        menuBuilder.appendHeader("Lanse");
        menuBuilder.appendMenu(lanseMenu());

        menuBuilder.appendHeader("Pierrogerria");
        menuBuilder.appendMenu(pierrogerriaMenu());

        menuBuilder.appendHeader("Centralna");
        menuBuilder.appendMenu(centralnaMenu());

        return menuBuilder.toString();
    }


    private String pierrogerriaMenu() {
        return getWebpageElement(PIERROGERIA_URL, "div.scroll-mini ul").replaceAll(" ZUPA 4.*",
                "").replace("do wyboru:", "").replaceAll("^\\-","");
    }


    public String lanseMenu() {

    }

}

