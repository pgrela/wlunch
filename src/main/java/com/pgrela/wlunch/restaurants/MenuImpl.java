package com.pgrela.wlunch.restaurants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.google.common.collect.ImmutableList;

public class MenuImpl implements Menu {
    private Collection<Calendar> possibleDates = new ArrayList<Calendar>();
    String menu;

    public MenuImpl(String menu, Collection<Calendar> possibleDates) {
        this.possibleDates = possibleDates;
        this.menu = menu;
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
}
