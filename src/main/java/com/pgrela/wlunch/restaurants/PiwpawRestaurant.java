package com.pgrela.wlunch.restaurants;

import java.util.Calendar;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.pgrela.wlunch.common.Restaurant;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.Post;

@Component
public class PiwpawRestaurant implements Restaurant {
    @Autowired
    private FacebookClient publicOnlyFacebookClient;

    @Override
    public String getName() {
        return "PiwPaw";
    }

    @Override
    public Menu getMenu() {
        Connection<Post> myFeed = publicOnlyFacebookClient.fetchConnection("piwpawsklep/feed", Post.class);
        Post menu = null;
        Pattern matcher = Pattern.compile("32\\. ", Pattern.MULTILINE | Pattern.DOTALL);
        for (Post post : myFeed.getData()) {
            String message = post.getMessage();
            if (message!=null && matcher.matcher(message).find()) {
                menu = post;
                break;
            }
        }
        if (menu != null){
            Calendar date = Calendar.getInstance();
            date.setTime(menu.getCreatedTime());
            return new MenuImpl(menu.getMessage(), Lists.newArrayList(date));
        }else{
            return new MenuImpl("Brak menu na dziś");
        }
    }

    @Override
    public boolean isOpened() {
        return true;
    }
}
