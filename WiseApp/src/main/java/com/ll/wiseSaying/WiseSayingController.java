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
        this.modify_ptn = Pattern.compile("수정\\?id=(\\d+)");
        this.remove_ptn = Pattern.compile("삭제\\?id=(\\d+)");
    }

    // To Service
    public void addWise() {
        String new_content = printAndInputContent();
        String new_author = printAndInputAuthor();
        int wise_id = service.createWiseAndGetId(new_content, new_author);
        printAddCommandCompleted(wise_id);
    }
    public void showList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        System.out.print(service.getWiseList());
    }
    public void removeWise(int id) {
        if (service.isWiseContains(id)) {
            service.removeWise(id);
            printRemoveCommandCompleted(id);
        }
        else printWiseIsNotExists(id);
    }
    public void modifyWise(int id) {
        if (!service.isWiseContains(id)) { printWiseIsNotExists(id); return;}
        WiseSaying current_wise = service.getWiseById(id);

        printPreviousContent(current_wise.getContent());
        String new_content = printAndInputContent();

        printPreviousAuthor(current_wise.getAuthor());
        String new_author = printAndInputAuthor();

        service.modifyWise(id, new_content, new_author);
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
    public void printWiseIsNotExists(int id) {
        System.out.println(id + "번 명언은 존재하지 않습니다.");
    }
    public void printPreviousContent(String content) {
        System.out.println("명언(기존) : " + content);
    }
    public void printPreviousAuthor(String author) {
        System.out.println("작가(기존) : " + author);
    }
    public String printAndInputContent() {
        System.out.print("명언 : "); return scanner.nextLine().trim();
    }
    public String printAndInputAuthor() {
        System.out.print("작가 : "); return scanner.nextLine().trim();
    }
    public void printAddCommandCompleted(final int id) {
        System.out.println(id + "번 명언이 등록되었습니다.");
    }
    public void printRemoveCommandCompleted(final int id) {
        System.out.println(id + "번 명언이 삭제되었습니다.");
    }

    // Private
    private final Pattern remove_ptn;
    private final Pattern modify_ptn;
    private final Scanner scanner;
    private final WiseSayingService service;

}
