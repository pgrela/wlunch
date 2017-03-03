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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LanseRestaurant implements Restaurant {

    private static final String URL = "http://wieszcozjesz.pl/1-Danie-dnia";
    private static final String SELECTOR = "div.page_content";
    private static final String WIESZ_CO_ZJESZ = "WieszCoZjesz";
    public static final Pattern DATE_PATTERN = Pattern.compile("([0-9]{1,2})[\\.\\-]([0-9]{1,2})\\.((20)?[0-9]{2})</p>");

    private TimeSource timeSource;
    private Downloader downloader;

    @Autowired
    public LanseRestaurant(TimeSource timeSource, Downloader downloader) {
        this.timeSource = timeSource;
        this.downloader = downloader;
    }

    @Override
    public String getName() {
        return WIESZ_CO_ZJESZ;
    }

    @Override
    public TodayMenu getMenu() {
        String dayOfWeek = timeSource.getTodayDayName();
        String pattern = dayOfWeek.toUpperCase(MenuTransformer.LOCALE_PL).substring(0, 1) + dayOfWeek.substring(
                1).replace("ą", "[ąĄ]") + "[^\n]*\n(.*\n.*\n.*\n.*\n.*)";
        String rawMenu = downloader.downloadElement(URL, SELECTOR);
        String menu = MenuTransformer.startWith(rawMenu)
                .unescapeHtmlEntities()
                .stripHtml()
                .stripEmptyLines()
                .extract(pattern)
                .getMenu();
        return new TodayMenuImpl(computeReliability(rawMenu), menu);
    }

    private Reliability computeReliability(String rawMenu) {
        Matcher matcher = DATE_PATTERN.matcher(rawMenu);
        if (matcher.find()) {
            Calendar today = timeSource.getTodaysCalendar();
            Calendar friday = timeSource.getTodaysCalendar();
            int fridayDayOfMonth = Integer.parseInt(matcher.group(2));
            int month = Integer.parseInt(matcher.group(3));
            friday.set(Calendar.YEAR, today.get(Calendar.YEAR));
            friday.set(Calendar.MONTH, month - 1);
            friday.set(Calendar.DAY_OF_MONTH, fridayDayOfMonth);
            Calendar toBeAdded = timeSource.getTodaysCalendar();
            toBeAdded.set(Calendar.YEAR, friday.get(Calendar.YEAR));
            toBeAdded.set(Calendar.WEEK_OF_YEAR, friday.get(Calendar.WEEK_OF_YEAR));
            toBeAdded.set(Calendar.DAY_OF_WEEK, today.get(Calendar.DAY_OF_WEEK));
            if (CalendarUtil.areTheSameDays(timeSource.getTodaysCalendar(), toBeAdded)) {
                return Reliability.CERTAIN;
            } else {
                return Reliability.LOW;
            }
        }
        return Reliability.UNKNOWN;
    }

    @Override
    public boolean isOpened() {
        String todayDayName = this.timeSource.getTodayDayName();
        return !todayDayName.equals("sobota") && !todayDayName.equals("niedziela");
    }
}
