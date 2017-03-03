package com.pgrela.wlunch.restaurants.impl;

import com.pgrela.wlunch.api.Reliability;
import com.pgrela.wlunch.api.Restaurant;
import com.pgrela.wlunch.api.TodayMenu;
import com.pgrela.wlunch.restaurants.TodayMenuImpl;
import com.pgrela.wlunch.restaurants.utils.Downloader;
import com.pgrela.wlunch.restaurants.menu.MenuTransformer;
import com.pgrela.wlunch.restaurants.utils.TimeSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BistroPolitechnikaRestaurant implements Restaurant {

    private static final String BISTRO_POLITECHNIKA_WARSZAWSKA = "Bistro Politechnika Warszawska";
    private static final String HTTP_WWW_BISTROPOLITECHNIKA_PL_MENU_HTML = "http://www.bistropolitechnika.pl/menu.html";
    private static final String SELECTOR = "#templatemo_content_right";

    private TimeSource timeSource;
    private Downloader downloader;

    @Autowired
    public BistroPolitechnikaRestaurant(TimeSource timeSource, Downloader downloader) {
        this.timeSource = timeSource;
        this.downloader = downloader;
    }

    @Override
    public String getName() {
        return BISTRO_POLITECHNIKA_WARSZAWSKA;
    }

    @Override
    public TodayMenu getMenu() {
        String menu = downloader.downloadElement(HTTP_WWW_BISTROPOLITECHNIKA_PL_MENU_HTML, SELECTOR);
        menu = new MenuTransformer(menu)
                .unescapeHtmlEntities()
                .stripHtml()
                .stripEmptyLines()
                .removeAll("(?s)poniedziałek.*")
                .getMenu();
        return new TodayMenuImpl(computeReliability(menu), menu);
    }

    @Override
    public boolean isOpened() {
        return true;
    }

    protected Reliability computeReliability(String text) {
        String todayDayName = this.timeSource.getTodayDayName().toLowerCase(MenuTransformer.LOCALE_PL);
        if (text.toLowerCase(MenuTransformer.LOCALE_PL).contains(todayDayName)) {
            return Reliability.HIGH;
        }
        return Reliability.LOW;
    }
}
