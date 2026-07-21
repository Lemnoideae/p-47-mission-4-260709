package com.ll.wiseSaying;

import java.util.Arrays;

public enum Command {
    EXIT("종료"),
    ERROR("오류"),
    ADD("등록"),
    SHOW("목록"),
    REMOVE("삭제"),
    MODIFY("수정"),
    BUILD("빌드");

    private final String name;
    Command(String name) { this.name = name; }

    public static Command changeNameToCmd(String name) {
        return Arrays.stream(Command.values())
                .filter(cmd -> cmd.name.equals(name))
                .findFirst()
                .orElse(ERROR);
    }
    public boolean isCmdErrored() {
        return this.equals(ERROR);
    }
}
