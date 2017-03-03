package com.pgrela.wlunch.hunger;

import com.pgrela.wlunch.api.Reliability;
import com.pgrela.wlunch.api.Restaurant;
import com.pgrela.wlunch.api.TodayMenu;
import com.pgrela.wlunch.api.exceptions.MenuException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ErrandBoy {
    private Collection<Restaurant> restaurants;

    private MenuBoard menuBoard;

    @Autowired
    public ErrandBoy(Collection<Restaurant> restaurants, MenuBoard menuBoard) {
        this.restaurants = restaurants;
        this.menuBoard = menuBoard;
    }

    private final static Logger LOG = LoggerFactory.getLogger(ErrandBoy.class);

    public String getMenuForToday() {

        restaurants.stream()
                .parallel()
                .filter(Restaurant::isOpened)
                .forEach(restaurant -> checkOutOpenedRestaurant(menuBoard, restaurant));

        return menuBoard.toString();
    }

    private void checkOutOpenedRestaurant(MenuBoard menuBoard, Restaurant restaurant) {
        LOG.info("Retrieving menu for {}", restaurant.getName());
        String textMenu = "";
        try {
            TodayMenu menu = restaurant.getMenu();

            textMenu = menu.getMenuText();
            if (!menu.estimateReliability().equals(Reliability.CERTAIN)) {
                textMenu = "The menu might be not up to date (!), its reliability is  " + menu.estimateReliability().name()
                        + "\n\n" + textMenu;
            }
        } catch (MenuException e) {
            LOG.warn("Failed to get menu from {}!", e, restaurant.getName());
            textMenu = String.format("Failed to get menu from %s!", e.getMessage());
        } finally {
            menuBoard.updateMenu(restaurant.getName(), textMenu);
        }
    }

    public List<String> whatRestaurantsDoYouKnow() {
        return restaurants.stream().map(Restaurant::getName).collect(Collectors.toList());
    }
}

