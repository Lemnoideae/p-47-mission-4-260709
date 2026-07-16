package com.ll.wiseSaying;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class WiseSayingController {
    int[] checkCommand(String cmd) {
        switch (cmd) {
            case "종료" -> {return new int[]{0, 0};}
            case "등록" -> {return new int[]{1, 0};}
            case "목록" -> {return new int[]{2, 0};}
            case "빌드" -> {return new int[]{3, 0};}
        }
        // 수정?id=X
        Matcher cmd_matcher = modify_pattern.matcher(cmd);
        if (cmd_matcher.find())
            return new int[]{4, Integer.parseInt(cmd_matcher.group(1))};

        // 삭제?id=X
        cmd_matcher = delete_pattern.matcher(cmd);
        if (cmd_matcher.find())
            return new int[]{5, Integer.parseInt(cmd_matcher.group(1))};

        return new int[]{-1, -1};
    }

    String printAndInputCommand(Scanner scanner) {
        System.out.print("명령) ");
        return scanner.nextLine();
    }

    String printAndInputContent(Scanner scanner) {
        System.out.print("명언 : ");
        return scanner.nextLine();
    }
    void printPrevContent(String content) {System.out.println("명언(기존) : " + content);}

    String printAndInputAuthor(Scanner scanner) {
        System.out.print("작가 : ");
        return scanner.nextLine();
    }
    void printPrevAuthor(String author) {System.out.println("작가(기존) : " + author);}

    void printEntranceMessage() {System.out.println("== 명언 앱 ==");}
    void printString(String str) {System.out.print(str);}

    void printIdWiseIsNotExisted(final int id) {
        System.out.println(id + "번 명언은 존재하지 않습니다.");
    }
    void printCommandIsNotExisted() {
        System.out.println("올바르지 않은 명령입니다.");
    }
    void printAddCommandCompleted(int added_id) {
        System.out.println(added_id + "번 명언이 등록되었습니다");
    }
    void printRemoveCommandCompleted(int removed_id) {
        System.out.println(removed_id + "번 명언이 삭제되었습니다.");
    }
    void printBuildCompleted() {
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    private final Pattern modify_pattern = Pattern.compile("수정\\?id=(\\d+)");
    private final Pattern delete_pattern = Pattern.compile("삭제\\?id=(\\d+)");
}