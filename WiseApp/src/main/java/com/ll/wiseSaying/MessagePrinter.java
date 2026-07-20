package com.ll.wiseSaying;

import java.util.Scanner;

public class MessagePrinter {
    public void printPageLine(int current_page, int total_pages) {
        System.out.print("페이지 : ");
        if (current_page == 1) System.out.print("[1]");
        else System.out.print("1");

        for (int page = 2; page <= total_pages; page++) {
            System.out.print(" / ");
            if (current_page == page) System.out.print("[" + page + "]");
            else System.out.print(page);
        }
    }
    public void printSearchedKeyword(Command cmd) {
        printOneDashLine();
        printKeywordType(cmd.keyword_type);
        printKeyword(cmd.keyword);
        printOneDashLine();
    }
    public void printListString(PageDto<WiseSaying> page_dto, String list_str) {
        printAttributes();
        printOneDashLine();
        System.out.print(list_str);
        printOneDashLine();
        printPageLine(page_dto.current_pages(), page_dto.max_pages());
    }

    public void printEntranceMessage() { System.out.println("== 명언 앱 =="); }

    public void printOneDashLine() { System.out.println("----------------------"); }

    public void printAttributes() { System.out.println("번호 / 작가 / 명언"); }

    public void printCommandIsNotExists() {
        System.out.println("존재하지 않는 명령입니다.");
    }

    public void printWiseIsNotExists(int id) {
        System.out.println(id + "번 명언은 존재하지 않습니다.");
    }

    public String printAndInputContent(Scanner scanner) {
        System.out.print("명언 : "); return scanner.nextLine().trim();
    }
    public String printAndInputNewContent(String content, Scanner scanner) {
        System.out.println("명언(기존) : " + content); return printAndInputContent(scanner);
    }
    public String printAndInputAuthor(Scanner scanner) {
        System.out.print("작가 : "); return scanner.nextLine().trim();
    }
    public String printAndInputNewAuthor(String author, Scanner scanner) {
        System.out.println("작가(기존) : " + author); return printAndInputContent(scanner);
    }

    public void printKeywordType(String type) { System.out.println("검색 타입 : " + type); }
    public void printKeyword(String keyword) { System.out.println("검색어 : " + keyword); }

    public void printAddCommandCompleted(final int id) {
        System.out.println(id + "번 명언이 등록되었습니다.");
    }
    public void printRemoveCommandCompleted(final int id) {
        System.out.println(id + "번 명언이 삭제되었습니다.");
    }
    public void printJsonBuildCompleted() {
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
