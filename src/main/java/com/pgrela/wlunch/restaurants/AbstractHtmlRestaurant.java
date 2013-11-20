package com.pgrela.wlunch.restaurants;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pgrela.wlunch.utils.Downloader;

public abstract class AbstractHtmlRestaurant extends AbstractRestaurant {

    abstract protected String getSelector();

    abstract protected String stripMenu(String menu);

    @Autowired
    protected Downloader downloader;



    protected String formatMenu(String menu) {
        menu = deindent(menu);
        menu = stripMenu(menu);
        return menu;
    }

    private String deindent(String text) {
        return text.replaceAll(" - ?", "\n").replaceAll("\n[ \t]+]", "\n");
    }

    @Override
    public Menu getMenu() {
        String text = downloader.downloadElement(getUrl(), getSelector());
        String menu = formatMenu(text);
        List<Calendar> possibleDates = getPossibleDates(text);
        return new MenuImpl(menu,possibleDates);
    }
}
