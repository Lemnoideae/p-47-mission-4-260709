package com.ll.wiseSaying;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatterns {
    private final String[] keyList;
    private final HashMap<String, Pattern> patternMap;

    public RegexPatterns() {
        Pattern jsonPattern = Pattern.compile("\\{" +
                "\\s*\"id\"\\s*:\\s*(\\d+)\\s*," +
                "\\s*\"content\"\\s*:\\s*\"([^\"]*)\"\\s*," +
                "\\s*\"author\"\\s*:\\s*\"([^\"]*)\"\\s*" +
                "}");

        this.patternMap = new HashMap<>();
        this.patternMap.put("json", jsonPattern);
        this.patternMap.put("id", Pattern.compile("(id)=(\\d+)"));
        this.patternMap.put("keywordType", Pattern.compile("(keywordType)=(.+)"));
        this.patternMap.put("keyword", Pattern.compile("(keyword)=(.+)"));
        this.patternMap.put("page", Pattern.compile("(page)=(\\d+)"));

        this.keyList = new String[] {"id", "keywordType", "keyword", "page"};
    }
    public String[] getKeyList() { return keyList; }

    public Pattern getJsonPattern() {
         return patternMap.get("json");
    }
    public Matcher getPatternMatcher(String key, String arg) {
        if (patternMap.containsKey(key)) return patternMap.get(key).matcher(arg);
        else throw new IllegalStateException("Unexpected value: " + arg);
    }
}
