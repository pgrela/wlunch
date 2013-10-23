package com.pgrela.wlunch.restaurants;

import com.pgrela.wlunch.utils.Downloader;

public abstract class AbstractHtmlRestaurant extends AbstractRestaurant {

    abstract protected String getSelector();

    abstract protected String stripMenu(String menu);

    Downloader downloader;

    protected AbstractHtmlRestaurant(Downloader downloader) {
        this.downloader = downloader;
    }

    @Override
    public String getMenu() {
        String text = downloader.downloadElement(getUrl(), getSelector());
        String menu = formatMenu(text);
        return menu;
    }

    private String formatMenu(String menu) {
        menu = deindent(menu);
        menu = stripMenu(menu);
        return menu;
    }

    private String deindent(String text) {
        return text.replaceAll(" - ?", "\n").replaceAll("\n[ \t]+]", "\n");
    }
}
