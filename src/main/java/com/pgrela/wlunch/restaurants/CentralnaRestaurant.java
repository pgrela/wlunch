package com.pgrela.wlunch.restaurants;

import com.pgrela.wlunch.utils.Downloader;

/**
 * @author: pgrela
 */
public class CentralnaRestaurant extends AbstractHtmlRestaurant {

    public CentralnaRestaurant(Downloader downloader) {
        super(downloader);
    }

    @Override
    protected String getUrl() {
        return "http://www.eurobistrocatering.pl/menu/stolowka-centralna-pw-politechnika.html";
    }

    protected String getSelector() {
        return "div.item-page";
    }

    @Override
    public String getName() {
        return "Centralna";
    }

    @Override
    protected String stripMenu(String menu) {
        return menu.replaceAll("(?s)^.*Menu.*?[0-9]{2}.*?\n", "");
    }
}
