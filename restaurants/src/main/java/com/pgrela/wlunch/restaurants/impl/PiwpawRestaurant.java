package com.pgrela.wlunch.restaurants.impl;

import com.pgrela.wlunch.api.Restaurant;
import com.pgrela.wlunch.api.TodayMenu;
import com.pgrela.wlunch.restaurants.TodayMenuImpl;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.regex.Pattern;

public class PiwpawRestaurant implements Restaurant {
    @Autowired
    private FacebookClient publicOnlyFacebookClient;

    public PiwpawRestaurant(FacebookClient publicOnlyFacebookClient) {
        this.publicOnlyFacebookClient = publicOnlyFacebookClient;
    }

    @Override
    public String getName() {
        return "PiwPaw";
    }

    @Override
    public TodayMenu getMenu() {
        Connection<Post> myFeed = publicOnlyFacebookClient.fetchConnection("piwpawsklep/feed", Post.class);
        Post menuPost = null;
        Pattern matcher = Pattern.compile("32\\. ", Pattern.MULTILINE | Pattern.DOTALL);
        for (Post post : myFeed.getData()) {
            String message = post.getMessage();
            if (message != null && matcher.matcher(message).find()) {
                menuPost = post;
                break;
            }
        }
        if (menuPost != null) {
            Calendar date = Calendar.getInstance();
            date.setTime(menuPost.getCreatedTime());
            return new TodayMenuImpl(menuPost.getMessage());
        } else {
            return new TodayMenuImpl("Brak menu na dziś");
        }
    }

    @Override
    public boolean isOpened() {
        return false;
    }
}
