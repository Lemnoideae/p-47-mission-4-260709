package com.ll.wiseSaying;

import java.io.IOException;
import java.util.Scanner;

public class App {
    public void run() {
        controller.printEntranceMessage();

        while (true) {
            int[] cmd_nums = controller.printAndInputCommand();
            switch (cmd_nums[0]) {
                case 0: System.exit(0);
                case 1: controller.addWise(); break;
                case 2: controller.showList(); break;
                case 3: controller.removeWise(); break;
                case 4: controller.modifyWise(); break;
                case 5: controller.buildJson(); break;
                case -1: controller.printCommandIsNotExists(); break;
                default: throw new IllegalArgumentException(
                        "Unknown command: " + cmd_nums[0]);
            }
        }
    }
    public App(Scanner scanner) {
        this.scanner = scanner;
        this.controller = new WiseSayingController(this.scanner,
                new WiseSayingService(new WiseSayingRepository()));
    }

    private final WiseSayingController controller;
    private final Scanner scanner;
}
