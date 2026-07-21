package com.ll.wiseSaying;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatterns {
    private final Pattern jsonPattern;
    private final Pattern idPattern;
    private final Pattern keywordTypePattern;
    private final Pattern keywordPattern;
    private final Pattern pagePattern;
    private final String[] keyList;

    public RegexPatterns() {
        this.jsonPattern = Pattern.compile("\\{" +
                "\\s*\"id\"\\s*:\\s*(\\d+)\\s*," +
                "\\s*\"content\"\\s*:\\s*\"([^\"]*)\"\\s*," +
                "\\s*\"author\"\\s*:\\s*\"([^\"]*)\"\\s*" +
                "}");

        this.idPattern = Pattern.compile("(id)=(\\d+)");
        this.keywordTypePattern = Pattern.compile("(keywordType)=(.+)");
        this.keywordPattern = Pattern.compile("(keyword)=(.+)");
        this.pagePattern = Pattern.compile("(page)=(\\d+)");

        this.keyList = new String[] {"id", "keywordType", "keyword", "page"};
    }
    public String[] getKeyList() { return keyList; }

    public Pattern getJsonPattern() {
         return jsonPattern;
    }

    public Matcher getPatternMatcher(String key, String arg) {
        return switch (key) {
            case "id" -> idPattern.matcher(arg);
            case "keywordType" -> keywordTypePattern.matcher(arg);
            case "keyword" -> keywordPattern.matcher(arg);
            case "page" -> pagePattern.matcher(arg);
            default -> throw new IllegalStateException("Unexpected value: " + arg);
        };
    }

}
