package com.pgrela.wlunch.restaurants.impl;

import com.pgrela.wlunch.api.Restaurant;
import com.pgrela.wlunch.api.TodayMenu;
import com.pgrela.wlunch.restaurants.utils.FacebookMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LaSirenaRestaurant implements Restaurant {
    private FacebookMenu facebookMenu;

    @Autowired
    public LaSirenaRestaurant(FacebookMenu facebookMenu) {
        this.facebookMenu = facebookMenu;
    }

    @Override
    public String getName() {
        return "BierHalle";
    }

    @Override
    public TodayMenu getMenu() {
        return facebookMenu.getMenu("873793949400701/posts", "Lunch");
    }

    @Override
    public boolean isOpened() {
        return false;
    }
}
