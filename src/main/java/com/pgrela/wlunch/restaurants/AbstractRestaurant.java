package com.pgrela.wlunch.restaurants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.testng.internal.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.pgrela.wlunch.common.Restaurant;

public abstract class AbstractRestaurant implements Restaurant {

    public static final Locale LOCALE_PL = Locale.forLanguageTag("pl-PL");

    abstract String getUrl();

    protected String applyDateWarning(List<Calendar> possibleDates) {
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        boolean isThereAValidDate = false;
        for (Calendar possibleDate : possibleDates) {
            if (isTheSameDay(possibleDate, today)) {
                isThereAValidDate = true;
            }
        }
        if (!isThereAValidDate) {
            return "The menu might be not up to date (!), it is possibly for " + joinDates(
                    possibleDates) + "\n\n";
        }
        return "";
    }

    private boolean isTheSameDay(Calendar first, Calendar second) {
        return first.get(Calendar.ERA) == second.get(Calendar.ERA)
                && first.get(Calendar.YEAR) == second.get(Calendar.YEAR)
                && first.get(Calendar.DAY_OF_YEAR) == second.get(Calendar.DAY_OF_YEAR);
    }

    private String joinDates(List<Calendar> dates) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Iterable<String> formattedDates = Iterables.transform(dates, new Function<Calendar, String>() {
            @Override
            public String apply(@Nullable Calendar calendar) {
                return sdf.format(calendar.getTime());
            }
        });
        return Joiner.on(", ").join(formattedDates);
    }

    protected List<Calendar> getPossibleDates(String text) {
        return Collections.emptyList();
    }
}