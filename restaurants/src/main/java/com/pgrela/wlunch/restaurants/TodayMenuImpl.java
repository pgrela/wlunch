package com.pgrela.wlunch.restaurants;

import com.pgrela.wlunch.api.Reliability;
import com.pgrela.wlunch.api.TodayMenu;

public class TodayMenuImpl implements TodayMenu {
    Reliability reliability;
    String menu;

    public TodayMenuImpl(Reliability reliability, String menu) {
        this.reliability = reliability;
        this.menu = menu;
    }

    @Override
    public Reliability estimateReliability() {
        return reliability;
    }

    public TodayMenuImpl(String menu) {
        this(Reliability.UNKNOWN, menu);
    }

    @Override
    public String getMenuText() {
        return menu;
    }

}
