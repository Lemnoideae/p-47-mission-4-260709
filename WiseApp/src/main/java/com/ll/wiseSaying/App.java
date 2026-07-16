package com.ll.wiseSaying;

import java.io.IOException;
import java.util.Scanner;

class App {
    void run() throws IOException {
        controller.printEntranceMessage();

        while (true) {
            scanFrontSubstring();
            int[] check_num = controller.checkCommand(command);
            switch (check_num[0]) {
                // 종료, 등록, 목록, 빌드, 수정, 삭제
                case 0: System.exit(0); break;
                case 1: addNewWise(); break;
                case 2: showList(); break;
                case 3: buildList(); break;
                case 4: modifyWise(check_num[1]); break;
                case 5: deleteWise(check_num[1]); break;
                case -1: controller.printCommandIsNotExisted(); break;
            }
        }
    }

    App() throws IOException {
        scanner = new Scanner(System.in);
        controller = new WiseSayingController();
        repository = new WiseSayingRepository(
                "WiseApp/db/wiseSaying/lastId.txt",
                "WiseApp/db/wiseSaying/data.json");
    }

    private void addNewWise() { repository.addNewWise(scanner, controller); }
    private void showList() { controller.printString(repository.getListString()); }
    private void buildList() throws IOException { repository.buildList(controller); }
    private void modifyWise(final int id) { repository.modifyWise(scanner, controller, id); }
    private void deleteWise(final int id) { repository.deleteWise(controller, id); }

    private void scanFrontSubstring() {
        while (true) {
            this.command = controller.printAndInputCommand(scanner);
            switch (command.substring(0, 2)) {
                case "종료", "등록", "목록", "삭제", "수정", "빌드": return;
                default: controller.printCommandIsNotExisted();
            }
        }
    }

    private final WiseSayingRepository repository;
    private final WiseSayingController controller;
    private final Scanner scanner;
    private String command = "";
}
