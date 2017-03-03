package com.pgrela.wlunch.restaurants.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;

@Component
public class Downloader {
    public String downloadElement(String url, String selector) {
        return getDocument(url).select(selector).html();
    }

    private Document getDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new UncheckedIOException("Can't download " + url, e);
        }
    }
}
