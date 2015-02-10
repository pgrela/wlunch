package com.pgrela.wlunch.restaurants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.junit.Test;

public class RegexTest {
    private static final String UTF8_CODING = "UTF-8";
    @Test
    public void regexTest() throws UnsupportedEncodingException {
        String column = "kolumn123456789098765432345678";
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            line.append(column).append(";");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            stringBuilder.append(line);
        }
        String s = stringBuilder.toString();
        System.out.println("Text size: " + s.length()/1024 +" kB");
        Map<String, List<String>> map = uriToParamValuesMap(s);
        for(Map.Entry<String, List<String>> entry : map.entrySet()){
            System.out.println(entry.getKey() + "=" + entry.getValue().toString());
        }
    }


    private Map<String, List<String>> uriToParamValuesMap(String url) throws UnsupportedEncodingException {
        final Map<String, List<String>> paramValuesMap = new HashMap<String, List<String>>();
        final StringTokenizer tokenizer = new StringTokenizer(url, "&");
        while (tokenizer.hasMoreElements()) {
            final String pair = tokenizer.nextToken();
            extractParamValueToMap(paramValuesMap, pair);
        }
        return paramValuesMap;
    }

    private void extractParamValueToMap(Map<String, List<String>> paramValuesMap, String pair) throws UnsupportedEncodingException {
        final int equalSignIndex = pair.indexOf('=');

        if(equalSignIndex<=0){
            return;
        }

        final String key = decodeURIEncoded(pair.substring(0, equalSignIndex));
        final String value = extractValueFromPair(pair, equalSignIndex);

        addKeyToMapIfNeeded(paramValuesMap, key);

        paramValuesMap.get(key).add(value);
    }

    private String extractValueFromPair(String pair, int equalSignIndex) throws UnsupportedEncodingException {
        if (pair.length() > equalSignIndex + 1) {
            return decodeURIEncoded(pair.substring(equalSignIndex + 1));
        }
        return null;
    }

    private void addKeyToMapIfNeeded(Map<String, List<String>> paramValuesMap, String key) {
        if (!paramValuesMap.containsKey(key)) {
            paramValuesMap.put(key, new LinkedList<String>());
        }
    }

    private String decodeURIEncoded(String string) throws UnsupportedEncodingException {
        if (isUrlEncodedContentType()) {
            return URLDecoder.decode(string, UTF8_CODING);
        }
        return string;
    }


    private boolean isUrlEncodedContentType() {
        return true;
    }
}
