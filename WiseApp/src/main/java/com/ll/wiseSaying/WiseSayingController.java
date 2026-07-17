package com.ll.wiseSaying;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WiseSayingController {
    public int[] printAndInputCommand() {
        System.out.print("명령) ");
        String cmd = scanner.nextLine().trim();
        switch (cmd) {
            case "종료": return new int[] {0, 0};
            case "등록": return new int[] {1, 0};
            case "목록": return new int[] {2, 0};
            case "빌드": return new int[] {5, 0};
        }
        Matcher cmd_mth;
        cmd_mth = remove_ptn.matcher(cmd);
        if (cmd_mth.find()) return new int[]{3, Integer.parseInt(cmd_mth.group(1))};

        cmd_mth = modify_ptn.matcher(cmd);
        if (cmd_mth.find()) return new int[]{4, Integer.parseInt(cmd_mth.group(1))};

        return new int[]{-1, -1};
    }
    public WiseSayingController(Scanner scanner, WiseSayingService service) {
        this.scanner = scanner;
        this.service = service;
        this.modify_ptn = Pattern.compile("수정\\?=id(\\d+)");
        this.remove_ptn = Pattern.compile("삭제\\?=id(\\d+)");
    }

    // To Service
    public void addWise() {
        service.addWise();
    }
    public void showList() {
        service.showList();
    }
    public void removeWise() {
        service.removeWise();
    }
    public void modifyWise() {
        service.modifyWise();
    }
    public void buildJson() {
        service.buildJson();
    }

    // Print Message | Error
    public void printEntranceMessage() {
        System.out.println("== 명언 앱 ==");
    }
    public void printCommandIsNotExists() {
        System.out.println("존재하지 않는 명령입니다.");
    }

    // Private
    private final Pattern remove_ptn;
    private final Pattern modify_ptn;
    private final Scanner scanner;
    private final WiseSayingService service;

}
