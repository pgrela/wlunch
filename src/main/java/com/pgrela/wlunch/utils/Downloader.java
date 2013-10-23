package com.pgrela.wlunch.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.pgrela.wlunch.common.MenuException;

/**
 * @author: pgrela
 */
public class Downloader {
    public String downloadElement(String url, String selector) {
        return getDocument(url).select(selector).text();
    }

    private Document getDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new MenuException("Can't download " + url);
        }
    }
}
