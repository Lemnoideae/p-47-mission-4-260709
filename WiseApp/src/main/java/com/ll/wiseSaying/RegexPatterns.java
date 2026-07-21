package com.ll.wiseSaying;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatterns {
    public final Pattern jsonPattern;
    public final Pattern idPattern;
    public final Pattern keywordTypePattern;
    public final Pattern keywordPattern;
    public final Pattern pagePattern;
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
