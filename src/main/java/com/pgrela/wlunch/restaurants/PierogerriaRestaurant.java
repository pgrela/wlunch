package com.pgrela.wlunch.restaurants;

import com.pgrela.wlunch.utils.Downloader;

/**
 * @author: pgrela
 */
public class PierogerriaRestaurant extends AbstractHtmlRestaurant {

    public PierogerriaRestaurant(Downloader downloader) {
        super(downloader);
    }

    @Override
    String getUrl() {
        return "http://www.pierrogeria.eu/";
    }

    protected String getSelector() {
        return "div.scroll-mini ul";
    }

    @Override
    public String getName() {
        return "Pierogerria";
    }

    @Override
    protected String stripMenu(String menu) {
        return menu.replaceAll(" ZUPA 4.*","").replace("do wyboru:", "").replaceAll("^\\-", "");
    }
}
