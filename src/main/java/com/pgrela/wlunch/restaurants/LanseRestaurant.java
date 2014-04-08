package com.pgrela.wlunch.restaurants;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.pgrela.wlunch.common.MenuException;
import com.pgrela.wlunch.utils.TimeSource;
@Component
public class LanseRestaurant extends AbstractRestaurant {

    @Autowired
    TimeSource timeSource;

    public String getUrl() {
        return "http://restauracja.lanse.pl/danie-dnia";
    }

    @Override
    public String getName() {
        return "Lanse";
    }

    @Override
    public Menu getMenu() {

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
            String docUrl = lanseMatcher.group(1);

            String document = docUrlToString(docUrl);
            String dayOfWeek = timeSource.getTodayDayName();
            String regexp = dayOfWeek.toUpperCase(LOCALE_PL).substring(0, 1) + dayOfWeek.substring(
                    1) + "[^\n]*\n(.*?)\n\t*\n";
            Matcher menuMatcher = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE + Pattern.DOTALL).matcher(document);
            Collection<Calendar> possibleDates = getPossibleDates(docUrl);
            if (!menuMatcher.find()) {
                return new MenuImpl(document,possibleDates);
            }
            return new MenuImpl(menuMatcher.group(1),possibleDates);
    }
    @Override
    protected List<Calendar> getPossibleDates(String text) {
        List<Calendar> dates = new ArrayList<Calendar>();
        Matcher matcher = Pattern.compile("([0-9]{1,2})[_/.\\-]([0-9]{1,2})[_/.\\-]((20)?[0-9]{2})\\.").matcher(text);
        if (matcher.find()) {
            Calendar today= timeSource.getTodaysCalendar();
            Calendar friday = timeSource.getTodaysCalendar();
            int day = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));
            friday.set(Calendar.YEAR, year);
            friday.set(Calendar.MONTH, month - 1);
            friday.set(Calendar.DAY_OF_MONTH, day);
            Calendar toBeAdded = timeSource.getTodaysCalendar();
            toBeAdded.set(Calendar.YEAR, friday.get(Calendar.YEAR));
            toBeAdded.set(Calendar.WEEK_OF_YEAR, friday.get(Calendar.WEEK_OF_YEAR));
            toBeAdded.set(Calendar.DAY_OF_WEEK, today.get(Calendar.DAY_OF_WEEK));
            dates.add(toBeAdded);
        }
        return dates;
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
