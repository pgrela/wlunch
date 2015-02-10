package com.pgrela.wlunch;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pgrela.wlunch.common.Restaurant;
import com.pgrela.wlunch.utils.MenuBuilder;

@Component
public class Menu {
    @Autowired
    List<Restaurant> restaurants;

    private final static Logger LOG = org.slf4j.LoggerFactory.getLogger(Menu.class);

    public String getMenuForToday() {

        MenuBuilder menuBuilder = new MenuBuilder();
        for (Restaurant restaurant : restaurants) {

            if(!restaurant.isOpened()){
                continue;
            }

            menuBuilder.appendHeader(restaurant.getName());
            LOG.info("Retrieving menu for {}", restaurant.getName());
            try {
                com.pgrela.wlunch.restaurants.Menu menu = restaurant.getMenu();
                menuBuilder.appendMenu(menu.toString());
            } catch (Exception e) {
                LOG.error("Failed to get menu from {}!", e, restaurant.getName());
                menuBuilder.appendMenu(e.getMessage());
            }
        }

        return menuBuilder.toString();
    }
}

