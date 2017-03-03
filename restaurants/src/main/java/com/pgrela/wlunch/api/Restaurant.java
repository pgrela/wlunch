package com.pgrela.wlunch.api;

public interface Restaurant {
    String getName();

    TodayMenu getMenu();

    default boolean isOpened() {
        return true;
    }
}
