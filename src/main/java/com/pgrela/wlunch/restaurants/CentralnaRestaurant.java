package com.pgrela.wlunch.restaurants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class CentralnaRestaurant extends AbstractHtmlRestaurant {

    @Override
    protected String getUrl() {
        return "http://www.eurobistrocatering.pl/menu/stolowka-centralna-pw-politechnika.html";
    }

    protected String getSelector() {
        return "div.item-page";
    }

    @Override
    public String getName() {
        return "Centralna";
    }

    @Override
    protected String stripMenu(String menu) {
        return menu.replaceAll("(?s)^.*Menu.*?[0-9]{2}.*?\n", "");
    }

    @Override
    protected List<Calendar> getPossibleDates(String text) {
        List<Calendar> dates = new ArrayList<Calendar>();
        Matcher matcher = Pattern.compile("([0-9]{1,2})[/\\.\\-]([0-9]{1,2})[/\\.\\-]((20)?[0-9]{2})").matcher(text);
        if (matcher.find()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int day = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month-1);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            dates.add(calendar);
        }
        return dates;
    }
}
