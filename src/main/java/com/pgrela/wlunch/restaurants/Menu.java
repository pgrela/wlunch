package com.pgrela.wlunch.restaurants;

interface Menu {
    boolean mightItBeForToday();
    String getPossibleDates();
    String toString();
}
