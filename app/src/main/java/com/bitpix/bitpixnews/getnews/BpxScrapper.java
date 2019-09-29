package com.bitpix.bitpixnews.getnews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BpxScrapper {

    private final String RSS_URL = "https://www.news18.com/rss/world.xml";
    private final String RSS_TAG = "item";
    private final String RSS_NEWS_TITLE = "title";
    private final String RSS_NEWS_LINK = "link";
    private final String RSS_NEWS_DESCRIPTION = "description";
    private final String RSS_NEWS_PUBDATE = "pubDate";

    public ArrayList<StructN18> getHeadlines() {
        ArrayList<StructN18> headlines = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(RSS_URL).get();
            Elements arr = doc.getElementsByTag(RSS_TAG);
            for(Element i : arr) {
                String link = i.selectFirst(RSS_NEWS_LINK).text();
                String title = i.selectFirst(RSS_NEWS_TITLE).text();
                String pubTime = getTimeEstimate(i.selectFirst(RSS_NEWS_PUBDATE).text());
                String imgUrl = extractUrl(i.selectFirst(RSS_NEWS_DESCRIPTION).text());
                headlines.add(new StructN18(link, title, pubTime, imgUrl));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return headlines;
    }

    private String getTimeEstimate(String pubDate) {
        String pubTime;
        int difference = getCurrentHour() - getNewsPubHour(pubDate);
        if(difference==0) {
            pubTime = "Published right now.";
        } else if(difference==1) {
            pubTime = "Published an hour ago.";
        } else if(difference>0) {
            pubTime = "Published " + difference + " hours ago.";
        } else {
            pubTime = "Published few hours ago.";
        }
        return pubTime;
    }

    private boolean isNumericString(String text) {
        for(int i=0; i<text.length(); i++) {
            int code = (int)text.charAt(i);
            if(code<48||code>57)
                return false;
        }
        return true;
    }

    private int getNewsPubHour(String pubDate) {
        String val = "0";
        StringTokenizer st = new StringTokenizer(pubDate ," ");
        while (st.hasMoreTokens()) {
            String target = st.nextToken();
            int count = 0;
            for(int i=0; i<target.length();i++) {
                if(target.charAt(i) == ':')
                    count+=1;
            }
            if(count > 1) {
                st = new StringTokenizer(target, ":");
                val = st.nextToken();
                if(isNumericString(val))
                    return Integer.parseInt(val);
            }
        }
        return Integer.parseInt(val);
    }

    private int getCurrentHour() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        StringTokenizer st = new StringTokenizer(sdf.format(new Date()), ":");
        String val = st.nextToken();
        if(isNumericString(val))
            return Integer.parseInt(val);
        return Integer.parseInt("0");
    }

    private String extractUrl(String text) {
        String containedUrl = "";
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);
        while (urlMatcher.find())  {
            containedUrl = (text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }
        return containedUrl;
    }
}
