package com.pgrela.wlunch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.pgrela.wlunch.common.Restaurant;
import com.pgrela.wlunch.restaurants.CentralnaRestaurant;
import com.pgrela.wlunch.restaurants.LanseRestaurant;
import com.pgrela.wlunch.restaurants.PierogerriaRestaurant;
import com.pgrela.wlunch.utils.Downloader;
import com.pgrela.wlunch.utils.MenuBuilder;

public class Menu {

    public static void main(String[] args) throws IOException {
        System.out.println(new Menu().getMenuForToday());
    }

    public String getMenuForToday() {
        List<Restaurant> restaurants = getRestaurants();

        MenuBuilder menuBuilder = new MenuBuilder();
        for (Restaurant restaurant : restaurants) {
            menuBuilder.appendHeader(restaurant.getName());
            menuBuilder.appendMenu(restaurant.getMenu());
        }

        return menuBuilder.toString();
    }

    private List<Restaurant> getRestaurants() {
        Downloader downloader = new Downloader();
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(new LanseRestaurant());
        restaurants.add(new CentralnaRestaurant(downloader));
        restaurants.add(new PierogerriaRestaurant(downloader));
        return restaurants;
    }
}

