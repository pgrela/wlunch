package com.pa2013.restaurants;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.pa2013.MenuException;

/**
 * @author: pgrela
 */
public abstract class AbstractHtmlRestaurant extends AbstractRestaurant {

    @Override
    public String getMenu() {
        String text = getDocument(getUrl()).select(getSelector()).text();
        String menu = formatMenu(text);
        return menu;
    }

    private String formatMenu(String text) {
        String menu = deindent(text);
        menu = stripMenu(text);
        return menu;
    }

    private String deindent(String text) {
        return text.replaceAll(" - ?", "\n").replaceAll("\n[ \t]+]", "\n");
    }

    abstract protected String getSelector();

    abstract protected String stripMenu(String menu);

    protected Document getDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new MenuException("Can't download " + url);
        }
    }
}
