package com.pgrela.wlunch.restaurants;

import java.util.Calendar;
import java.util.Collection;

public interface Menu {
    boolean isThisMenuForDate(Calendar date);
    Collection<Calendar> getPossibleDates();
    String toString();
}
