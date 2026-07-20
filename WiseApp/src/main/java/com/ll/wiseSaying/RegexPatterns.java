package com.ll.wiseSaying;

import java.util.regex.Pattern;

public class RegexPatterns {
    public final Pattern json_ptn;
    public final Pattern remove_cmd;
    public final Pattern modify_cmd;
    public final Pattern search_cmd;
    public final Pattern show_cmd_with_pages;

    public RegexPatterns() {
        this.json_ptn = Pattern.compile("\\{" +
                "\\s*\"id\"\\s*:\\s*(\\d+)\\s*," +
                "\\s*\"content\"\\s*:\\s*\"([^\"]*)\"\\s*," +
                "\\s*\"author\"\\s*:\\s*\"([^\"]*)\"\\s*" +
                "}");
        this.remove_cmd = Pattern.compile("삭제\\?id=(\\d+)");
        this.modify_cmd = Pattern.compile("수정\\?id=(\\d+)");
        this.search_cmd = Pattern.compile("목록\\?keywordType=([^&]+)&keyword=(.+)");
        this.show_cmd_with_pages = Pattern.compile("목록\\?page=(\\d+)");
    }
}
