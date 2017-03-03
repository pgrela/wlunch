package com.pgrela.wlunch.restaurants.menu;

import com.pgrela.wlunch.api.exceptions.MenuException;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuTransformer {
    public static final Locale LOCALE_PL = Locale.forLanguageTag("pl-PL");

    private String menu;

    public static MenuTransformer startWith(String menu) {
        return new MenuTransformer(menu);
    }

    public MenuTransformer(String menu) {
        this.menu = menu;
    }

    public MenuTransformer stripHtml() {
        menu = menu.replaceAll("<br[^>]*>","\n").replaceAll("<[^>]+>", "");
        return this;
    }

    public MenuTransformer stripEmptyLines() {
        menu = menu.replaceAll("(?s)(\n[\\s\\xa0]*)+\n", "\n");
        return this;
    }

    public MenuTransformer unescapeHtmlEntities() {
        menu = StringEscapeUtils.unescapeHtml4(menu);
        return this;
    }

    public MenuTransformer removeAll(String pattern) {
        menu = menu.replaceAll(pattern, "");
        return this;
    }

    public String getMenu() {
        return menu;
    }

    public MenuTransformer extract(String pattern) {
        Matcher menuMatcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(menu);
        if (!menuMatcher.find()) {
            throw new MenuException("Pattern " + pattern + " not found");
        }
        menu = menuMatcher.group(1);
        return this;
    }
}
