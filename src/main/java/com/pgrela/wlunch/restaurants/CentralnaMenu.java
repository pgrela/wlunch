package com.pgrela.wlunch.restaurants;

public class CentralnaMenu implements Menu {


    @Override
    public boolean mightItBeForToday() {
        return false;
    }

    @Override
    public String getPossibleDates() {
        return null;
    }
}
