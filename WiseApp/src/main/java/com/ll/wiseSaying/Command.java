package com.ll.wiseSaying;

public class Command {
    public final CommandId command_id;
    public final Integer target_wise_id;
    public final Integer page_number;
    public final String keyword_type;
    public final String keyword;

    private static final int DEFAULT_ID = -1;
    private static final int DEFAULT_PAGE = 1;
    private static final String DEFAULT_TYPE = "";
    private static final String DEFAULT_KEYWORD = "";
    private boolean is_app_must_search_wise_by_keyword;

    public Command(CommandId cmd_id, int target_wise_id, int page_number,
                   String keyword_type, String keyword) {
        this.command_id = cmd_id;
        this.target_wise_id = target_wise_id;
        this.page_number = page_number;
        this.keyword_type = keyword_type;
        this.keyword = keyword;
        is_app_must_search_wise_by_keyword = false;
    }
    public Command(CommandId cmd_id, String keyword_type, String keyword) {
        this(cmd_id, DEFAULT_ID, DEFAULT_PAGE, keyword_type, keyword);
        is_app_must_search_wise_by_keyword = true;
    }
    public Command(CommandId cmd_id, int target_wise_id, int page_number) {
        this(cmd_id, target_wise_id, page_number, DEFAULT_TYPE, DEFAULT_KEYWORD);
    }
    public Command(CommandId cmd_id, int target_wise_id) {
        this(cmd_id, target_wise_id, DEFAULT_PAGE, DEFAULT_TYPE, DEFAULT_KEYWORD);
    }
    public Command(CommandId cmd_id) {
        this(cmd_id, DEFAULT_ID, DEFAULT_PAGE, DEFAULT_TYPE, DEFAULT_KEYWORD);
    }

    public final boolean isAppMustSearchWiseByKeyword() {
        return is_app_must_search_wise_by_keyword;
    }
}
