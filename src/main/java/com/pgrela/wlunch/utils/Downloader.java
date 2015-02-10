package com.pgrela.wlunch.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.pgrela.wlunch.common.MenuException;

@Component
public class Downloader {
    public String downloadElement(String url, String selector) {
        return getDocument(url).select(selector).html();
    }

    private Document getDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new MenuException("Can't download " + url);
        }
    }
}
