package com.pgrela.wlunch.restaurants;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.pgrela.wlunch.common.MenuException;


public class LanseRestaurant extends AbstractRestaurant {


    public String getUrl() {
        return "http://restauracja.lanse.pl/danie-dnia";
    }

    @Override
    public String getName() {
        return "Lanse";
    }

    @Override
    public String getMenu() {

        try {
            Pattern lansePattern = Pattern.compile(
                    "<a[^>]+href=\"(http://images.lanse.pl/file/[^>]+.doc)\"><img [^>]+src=\"http://images.lanse.pl/image/ikona-word%5B1%5D.jpg\"[^>]+/></a>");
            String lansePage;
            try{
                lansePage = CharStreams.toString(
                        new InputStreamReader(new URL(getUrl()).openStream(),
                                Charsets.UTF_8));
            }catch (IOException e){
                throw new MenuException(e.getMessage());
            }
            Matcher lanseMatcher = lansePattern.matcher(lansePage);
            if (!lanseMatcher.find()) {
                throw new MenuException("no link to doc file!");
            }
            String document = docUrlToString(lanseMatcher.group(1));
            String dayOfWeek = new SimpleDateFormat("EEEE", LOCALE_PL).format(new Date());
            String regexp = dayOfWeek.toUpperCase(LOCALE_PL).substring(0, 1) + dayOfWeek.substring(
                    1) + "[^\n]*\n(.*?)\n\t*\n";
            Matcher menuMatcher = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE + Pattern.DOTALL).matcher(document);
            if (!menuMatcher.find()) {
                throw new MenuException("no menu for today!");
            }
            return menuMatcher.group(1);
        } catch (MenuException e) {
            return e.getMessage();
        }
    }

    private String docUrlToString(String url) {
        HWPFDocument document;
        try {
            document = new HWPFDocument(new URL(url).openStream());
        } catch (MalformedURLException e) {
            throw new MenuException("wrong url to doc file!");
        } catch (IOException e) {
            throw new MenuException("couldn't download doc file");
        }
        return new WordExtractor(document).getText();
    }
}
