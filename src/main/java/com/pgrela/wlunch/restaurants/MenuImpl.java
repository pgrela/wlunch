package com.pgrela.wlunch.restaurants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.testng.internal.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import javax.jws.WebService;

public class MenuImpl implements Menu {
    private Collection<Calendar> possibleDates = new ArrayList<Calendar>();
    String menu;

    public MenuImpl(String menu, Collection<Calendar> possibleDates) {
        this.possibleDates = possibleDates;
        this.menu = applyDateWarning() + menu;
    }

    public MenuImpl(String menu){
        this(menu, Collections.<Calendar>emptyList());
    }

    @Override
    public boolean isThisMenuForDate(Calendar date) {
        return possibleDates.contains(date);
    }

    @Override
    public Collection<Calendar> getPossibleDates() {
        return ImmutableList.copyOf(possibleDates);
    }

    @Override
    public String toString() {
        return menu;
    }

    protected String applyDateWarning() {
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

    private String joinDates(Collection<Calendar> dates) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Iterable<String> formattedDates = Iterables.transform(dates, new Function<Calendar, String>() {
            @Override
            public String apply(@Nullable Calendar calendar) {
                return sdf.format(calendar.getTime());
            }
        });
        return Joiner.on(", ").join(formattedDates);
    }
}
