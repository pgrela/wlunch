package com.pgrela.wlunch.restaurants.impl;

import com.pgrela.wlunch.api.Restaurant;
import com.pgrela.wlunch.api.TodayMenu;
import com.pgrela.wlunch.restaurants.utils.FacebookMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BierhalleMarszalkowskaRestaurant implements Restaurant {
    private FacebookMenu facebookMenu;

    @Autowired
    public BierhalleMarszalkowskaRestaurant(FacebookMenu facebookMenu) {
        this.facebookMenu = facebookMenu;
    }

    @Override
    public String getName() {
        return "BierHalle";
    }

    @Override
    public TodayMenu getMenu() {
        return facebookMenu.getMenu("bierhalle.marszalkowska/feed", "lunch");
    }

    @Override
    public boolean isOpened() {
        return false;
    }
}
