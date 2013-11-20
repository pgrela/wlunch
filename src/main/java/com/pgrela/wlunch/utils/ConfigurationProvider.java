package com.pgrela.wlunch.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component
public class ConfigurationProvider {

    public static final String WLUNCH_PREFIX = "wlunch.";
    Properties properties;

    public ConfigurationProvider() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(System.getProperty("user.home"), ".wlunch")));
        } catch (IOException e) {
        }
        //properties = System.getProperties();
        //System.out.println(properties.toString());
        for (String property : System.getProperties().stringPropertyNames()) {
            //System.out.println(property + ":" + System.getProperty(property));
            if (property.startsWith(WLUNCH_PREFIX)) {
                properties.setProperty(property.substring(WLUNCH_PREFIX.length()), System.getProperty(property));
                //System.out.println("        Added");
            }
        }
    }

    public boolean isMailingEnabled() {
        return Boolean.valueOf(zeroOneToFalseTrue(properties.getProperty("mailing.enabled", "0")));
    }

    public String getMailingEmailAddress() {
        return getRequiredProperty("mailing.email");
    }

    public String getMailingPort() {
        return properties.getProperty("mailing.smtp.port", "587");
    }

    public String getMailingHost() {
        return getRequiredProperty("mailing.smtp.host");
    }

    public String getMailingPassword() {
        return getRequiredProperty("mailing.smtp.password");
    }

    public String getMailingUsername() {
        return getRequiredProperty("mailing.smtp.username");
    }

    public String getMailingFromName() {
        return properties.getProperty("mailing.smtp.from.name", getRequiredProperty("mailing.smtp.username"));
    }

    public String getMailingFromEmail() {
        return properties.getProperty("mailing.smtp.from.email", getRequiredProperty("mailing.smtp.username"));
    }
    public boolean isSecretKeyEnabled(){
        return properties.containsKey("mailing.secret");
    }
    public String getSecretKey(){
        return properties.getProperty("mailing.secret");
    }

    private String getRequiredProperty(String name) {
        String value = properties.getProperty(name);
        if (Strings.isNullOrEmpty(value)) {
            throw new IllegalStateException("No property '" + name + "' configured.");
        }
        return value;
    }


    private String zeroOneToFalseTrue(String zeroOne) {
        if (zeroOne.equals("0")) {
            return "false";
        }
        if (zeroOne.equals("1")) {
            return "true";
        }
        return "false";
    }
}
