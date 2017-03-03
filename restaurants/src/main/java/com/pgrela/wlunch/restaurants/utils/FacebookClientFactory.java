package com.pgrela.wlunch.restaurants.utils;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacebookClientFactory {

    private static final String ACCESS_TOKEN = "466866730081742|Ba-9gTeCaSfgY_AK_bFkPmd7EQc";
    private static final String APP_SECRET = "a9649e0e9ce6d0420dc73637d8013769";

    @Bean
    FacebookClient facebookClient() {
        return new DefaultFacebookClient(ACCESS_TOKEN, APP_SECRET);
    }
}
