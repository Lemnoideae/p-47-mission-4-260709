package com.ll.wiseSaying;

import java.util.Scanner;

public class MessagePrinter {
    public void printEntranceMessage() { System.out.println("== 명언 앱 =="); }
    public void printOneDashLine() { System.out.println("----------------------"); }
    public void printAttributes() { System.out.println("번호 / 작가 / 명언"); }
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
    public String printAndInputContent(Scanner scanner) {
        System.out.print("명언 : "); return scanner.nextLine().trim();
    }
    public String printAndInputAuthor(Scanner scanner) {
        System.out.print("작가 : "); return scanner.nextLine().trim();
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
