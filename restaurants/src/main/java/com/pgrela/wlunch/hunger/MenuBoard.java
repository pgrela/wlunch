package com.pgrela.wlunch.hunger;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MenuBoard {

    private Map<String, String> menus = new HashMap<>();

    public synchronized MenuBoard updateMenu(String restaurantName, String menu) {
        menus.put(restaurantName, menu);
        return this;
    }

    private String indent(String paragraph) {
        return paragraph.replaceAll("\n", "\n\t");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : menus.entrySet()) {
            builder.append(entry.getKey()).append(":\n\t").append(indent(entry.getValue())).append("\n\n");
        }

        return builder.toString();
    }

    public String getMenu(String restaurantName) {
        if (!menus.containsKey(restaurantName)) {
            throw new IllegalArgumentException("No such menu!");
        }
        return menus.get(restaurantName);
    }
}
