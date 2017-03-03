package com.pgrela.wlunch.restaurants.impl;

import com.pgrela.wlunch.api.Reliability;
import com.pgrela.wlunch.api.Restaurant;
import com.pgrela.wlunch.api.TodayMenu;
import com.pgrela.wlunch.restaurants.TodayMenuImpl;
import com.pgrela.wlunch.utils.CalendarUtil;
import com.pgrela.wlunch.restaurants.utils.Downloader;
import com.pgrela.wlunch.restaurants.menu.MenuTransformer;
import com.pgrela.wlunch.restaurants.utils.TimeSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CentralnaRestaurant implements Restaurant {

    private static final String URL = "http://www.eurobistrocatering.pl/menu/stolowka-centralna-pw-politechnika.html";
    private static final String SELECTOR = "div.item-page";
    private static final String STOŁÓWKA_CENTRALNA = "Stołówka Centralna";

    private Downloader downloader;

    private TimeSource timeSource;
    public static final Pattern DATE_PATTERN = Pattern.compile("([0-9]{1,2})[/\\.\\-]([0-9]{1,2})[/\\.\\-]((20)?[0-9]{2})");

    @Autowired
    public CentralnaRestaurant(Downloader downloader, TimeSource timeSource) {
        this.downloader = downloader;
        this.timeSource = timeSource;
    }

    @Override
    public String getName() {
        return STOŁÓWKA_CENTRALNA;
    }

    @Override
    public TodayMenu getMenu() {
        String menu = MenuTransformer.startWith(downloader.downloadElement(URL, SELECTOR))
                .unescapeHtmlEntities()
                .stripHtml()
                .removeAll("(?s)^.*TodayMenu.*?[0-9]{2}.*?\n")
                .removeAll("(?s)UWAGA.*")
                .stripEmptyLines()
                .getMenu();

        return new TodayMenuImpl(computeReliability(menu), menu);
    }

    private Reliability computeReliability(String text) {
        Matcher dateMatcher = DATE_PATTERN.matcher(text);
        if (dateMatcher.find()) {
            Calendar dateFromMenu = Calendar.getInstance();
            dateFromMenu.setTime(new Date());
            int day = Integer.parseInt(dateMatcher.group(1));
            int month = Integer.parseInt(dateMatcher.group(2));
            int year = Integer.parseInt(dateMatcher.group(3));
            dateFromMenu.set(Calendar.YEAR, year);
            dateFromMenu.set(Calendar.MONTH, month - 1);
            dateFromMenu.set(Calendar.DAY_OF_MONTH, day);
            if (CalendarUtil.areTheSameDays(timeSource.getTodaysCalendar(), dateFromMenu)) {
                return Reliability.CERTAIN;
            } else {
                return Reliability.LOW;
            }
        }
        return Reliability.UNKNOWN;
    }

}
