package com.pgrela.wlunch.restaurants.utils;

import com.pgrela.wlunch.api.Reliability;
import com.pgrela.wlunch.api.TodayMenu;
import com.pgrela.wlunch.restaurants.TodayMenuImpl;
import com.pgrela.wlunch.utils.CalendarUtil;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

@Component
public class FacebookMenu {

    private FacebookClient publicOnlyFacebookClient;

    private TimeSource timeSource;

    @Autowired
    public FacebookMenu(FacebookClient publicOnlyFacebookClient, TimeSource timeSource) {
        this.publicOnlyFacebookClient = publicOnlyFacebookClient;
        this.timeSource = timeSource;
    }


    public TodayMenu getMenu(String profile, String lookupPattern) {
        Connection<Post> feed = publicOnlyFacebookClient.fetchConnection(profile, Post.class);
        Post menuPost = null;
        Pattern matcher = Pattern.compile(lookupPattern, Pattern.MULTILINE);
        for (Post post : feed.getData()) {
            String message = post.getMessage();
            if (message != null && matcher.matcher(message).find()) {
                menuPost = post;
                break;
            }
        }
        if (menuPost != null) {
            Reliability reliability = getMenuReliability(menuPost.getCreatedTime());
            return new TodayMenuImpl(reliability, menuPost.getMessage());
        } else {
            return new TodayMenuImpl("No menu for Today");
        }
    }

    private Reliability getMenuReliability(Date postCreatedTime) {
        Calendar postDate = Calendar.getInstance();
        postDate.setTime(postCreatedTime);
        return CalendarUtil.areTheSameDays(timeSource.getTodaysCalendar(), postDate)
                ? Reliability.CERTAIN : Reliability.ZERO;
    }
}
