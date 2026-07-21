package com.ll.wiseSaying;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatterns {
    public final Pattern json_ptn;
    public final Pattern id_ptn;
    public final Pattern keyword_type_ptn;
    public final Pattern keyword_ptn;
    public final Pattern page_ptn;
    private final String[] key_list;

    public RegexPatterns() {
        this.json_ptn = Pattern.compile("\\{" +
                "\\s*\"id\"\\s*:\\s*(\\d+)\\s*," +
                "\\s*\"content\"\\s*:\\s*\"([^\"]*)\"\\s*," +
                "\\s*\"author\"\\s*:\\s*\"([^\"]*)\"\\s*" +
                "}");

        this.id_ptn = Pattern.compile("(id)=(\\d+)");
        this.keyword_type_ptn = Pattern.compile("(keywordType)=(.+)");
        this.keyword_ptn = Pattern.compile("(keyword)=(.+)");
        this.page_ptn = Pattern.compile("(page)=(\\d+)");

        this.key_list = new String[] {"id", "keywordType", "keyword", "page"};
    }
    public String[] getKeyList() { return key_list; }

    public Matcher getPatternMatcher(String key, String arg) {
        return switch (key) {
            case "id" -> id_ptn.matcher(arg);
            case "keywordType" -> keyword_type_ptn.matcher(arg);
            case "keyword" -> keyword_ptn.matcher(arg);
            case "page" -> page_ptn.matcher(arg);
            default -> throw new IllegalStateException("Unexpected value: " + arg);
        };
    }

}
