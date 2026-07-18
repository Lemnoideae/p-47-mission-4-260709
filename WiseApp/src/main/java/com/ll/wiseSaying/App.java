package com.ll.wiseSaying;

import java.io.IOException;
import java.util.Scanner;

public class App {
    public void run() throws IOException {
        controller.printEntranceMessage();
        while (is_app_working) loopStart();
    }
    public App(Scanner scanner) throws IOException {
        is_app_working = true;
        this.controller = new WiseSayingController(scanner,
                new WiseSayingService(new WiseSayingRepository(
                        "db/wiseSaying/lastId.txt",
                        "db/wiseSaying/data.json")));
    }

    // Private :
    private void loopStart() throws IOException {
        int[] cmd_nums = controller.printAndInputCommand();
        switch (cmd_nums[0]) {
            case 0: is_app_working = false; return;
            case 1: controller.addWise(); break;
            case 2: controller.showList(); break;
            case 3:
                controller.removeWise(cmd_nums[1]);
                break;
            case 4:
                controller.modifyWise(cmd_nums[1]);
                break;
            case 5:
                controller.buildJson();
                break;
            case -1:
                controller.printCommandIsNotExists();
                break;
            default:
                throw new IllegalArgumentException(
                        "Unknown command: " + cmd_nums[0]);
        }
    }

    private boolean is_app_working;
    private final WiseSayingController controller;
}
