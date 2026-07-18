package com.ll.wiseSaying;

public class Command {
    public Command(CommandId cmd_id, int target_wise_id, String keyword_type, String keyword) {
        this.command_id = cmd_id;
        this.target_wise_id = target_wise_id;
        this.keyword_type = keyword_type;
        this.keyword = keyword;
        is_app_must_search_wise_by_keyword = false;
    }
    public Command(CommandId cmd_id, String keyword_type, String keyword) {
        this(cmd_id, 0, keyword_type, keyword);
        is_app_must_search_wise_by_keyword = true;
    }
    public Command(CommandId cmd_id, int target_wise_id) {
        this(cmd_id, target_wise_id, null, null);
    }
    public Command(CommandId cmd_id) {
        this(cmd_id, 0, null, null);
    }

    public final boolean isAppMustSearchWiseByKeyword() {
        return is_app_must_search_wise_by_keyword;
    }

    public final String keyword_type;
    public final String keyword;
    public final Integer target_wise_id;
    public final CommandId command_id;

    // Private
    private boolean is_app_must_search_wise_by_keyword;
}
