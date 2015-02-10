package com.pgrela.wlunch.restaurants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pgrela.wlunch.utils.TimeSource;
@Component
public class LanseRestaurant extends AbstractHtmlRestaurant {

    @Autowired
    TimeSource timeSource;

    public String getUrl() {
        return "http://wieszcozjesz.pl/1-Danie-dnia";
    }

    @Override
    public String getName() {
        return "Lanse";
    }

    @Override
    protected String getSelector() {
        return "div.page_content";
    }

    @Override
    protected String stripMenu(String menu) {
        String dayOfWeek = timeSource.getTodayDayName();
        String regexp = dayOfWeek.toUpperCase(LOCALE_PL).substring(0, 1) + dayOfWeek.substring(
                1).replace("ą","[ąĄ]") + "[^\n]*\n(.*?)";
        Matcher menuMatcher = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE + Pattern.DOTALL).matcher(menu);
        if (!menuMatcher.find()) {
            return menu;
        }
        return menuMatcher.group(1);
    }

    @Override
    protected List<Calendar> getPossibleDates(String text) {
        List<Calendar> dates = new ArrayList<Calendar>();
        Matcher matcher = Pattern.compile("([0-9]{1,2})\\.([0-9]{1,2})\\.(20[0-9]{2})").matcher(text);
        if (matcher.find()) {
            Calendar today= timeSource.getTodaysCalendar();
            Calendar friday = timeSource.getTodaysCalendar();
            int day = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));
            friday.set(Calendar.YEAR, year);
            friday.set(Calendar.MONTH, month - 1);
            friday.set(Calendar.DAY_OF_MONTH, day);
            Calendar toBeAdded = timeSource.getTodaysCalendar();
            toBeAdded.set(Calendar.YEAR, friday.get(Calendar.YEAR));
            toBeAdded.set(Calendar.WEEK_OF_YEAR, friday.get(Calendar.WEEK_OF_YEAR));
            toBeAdded.set(Calendar.DAY_OF_WEEK, today.get(Calendar.DAY_OF_WEEK));
            dates.add(toBeAdded);
        }
        return dates;
    }
}
