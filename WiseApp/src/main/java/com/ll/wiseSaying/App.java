package com.ll.wiseSaying;

import java.io.IOException;
import java.util.Scanner;

public class App {
    public void run() throws IOException {
        controller.msg.printEntranceMessage();
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
        final Command cmd = controller.printAndInputCommand();
        switch (cmd.command_id) {
            case EXIT: is_app_working = false; return;
            case ADD: controller.addWise(); break;
            case SHOW: controller.showList(cmd); break;
            case REMOVE: controller.removeWise(cmd.target_wise_id); break;
            case MODIFY: controller.modifyWise(cmd.target_wise_id); break;
            case BUILD: controller.buildJson(); break;
            case ERROR: controller.msg.printCommandIsNotExists(); break;
        }
    }

    private boolean is_app_working;
    private final WiseSayingController controller;
}
