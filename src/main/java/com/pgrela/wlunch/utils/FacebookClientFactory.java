package com.pgrela.wlunch.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

@Configuration
public class FacebookClientFactory {
    @Bean
    FacebookClient facebookClient() {
        return new DefaultFacebookClient(
                "466866730081742|Ba-9gTeCaSfgY_AK_bFkPmd7EQc", "a9649e0e9ce6d0420dc73637d8013769");
    }
}
