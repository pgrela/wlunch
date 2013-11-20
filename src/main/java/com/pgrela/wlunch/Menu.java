package com.pgrela.wlunch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pgrela.wlunch.common.Restaurant;
import com.pgrela.wlunch.utils.MenuBuilder;
@Component
public class Menu {
    @Autowired
    List<Restaurant> restaurants;

    public String getMenuForToday() {

        MenuBuilder menuBuilder = new MenuBuilder();
        for (Restaurant restaurant : restaurants) {
            menuBuilder.appendHeader(restaurant.getName());
            com.pgrela.wlunch.restaurants.Menu menu = restaurant.getMenu();
            menuBuilder.appendMenu(menu.toString());
        }

        return menuBuilder.toString();
    }
}

