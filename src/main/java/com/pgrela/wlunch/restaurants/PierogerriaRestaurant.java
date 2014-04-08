package com.pgrela.wlunch.restaurants;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class PierogerriaRestaurant extends AbstractHtmlRestaurant {


    @Override
    String getUrl() {
        return "http://pierrogeria.upmenu.pl/menu";
    }

    protected String getSelector() {
        return "div.products div.product-group:contains(LUNCH) + div";
    }

    @Override
    public String getName() {
        return "Pierogerria";
    }

    @Override
    public Menu getMenu() {
        return super.getMenu();
    }

    @Override
    protected String stripMenu(String menu) {
        return menu.replaceFirst("^[^\\+]+\\+[^\\+]+\\+ +","").replaceAll("[0-9]+,[0-9]{2} zł","").replaceAll(" +\\+ +",
                "\n");
    }

    @Override
    protected List<Calendar> getPossibleDates(String text) {
        return Lists.newArrayList(Calendar.getInstance());
    }
}
