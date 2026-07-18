package com.ll.wiseSaying;

import java.util.regex.Pattern;

public class RegexPatterns {
    public RegexPatterns() {
        this.show_cmd_with_pages = Pattern.compile("목록\\?page=(\\d+)");
        this.search_cmd = Pattern.compile("목록\\?keywordType=([^&]+)&keyword=(.+)");
        this.modify_cmd = Pattern.compile("수정\\?id=(\\d+)");
        this.remove_cmd = Pattern.compile("삭제\\?id=(\\d+)");
    }
    public final Pattern show_cmd_with_pages;
    public final Pattern search_cmd;
    public final Pattern modify_cmd;
    public final Pattern remove_cmd;
}
