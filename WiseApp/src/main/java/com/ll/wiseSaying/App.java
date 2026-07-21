package com.ll.wiseSaying;

import java.io.IOException;
import java.util.Scanner;

public class App {
    private final WiseSayingController controller;
    private boolean is_app_working;

    public void run() throws IOException {
        controller.msg.printEntranceMessage();
        while (is_app_working) loopStart();
    }
    public App(Scanner scanner) throws IOException {
        is_app_working = true;
        this.controller = new WiseSayingController(scanner,
                new WiseSayingService(new WiseSayingRepository(
                        new RegexPatterns().json_ptn,
                        "db/wiseSaying/lastId.txt",
                        "db/wiseSaying/data.json")));
    }

    private void loopStart() throws IOException {
        final Rq rq = controller.printAndInputRequest();
        switch (rq.getCmd()) {
            case EXIT: is_app_working = false; return;
            case ADD: controller.addWise(); break;
            case SHOW: controller.showList(rq); break;
            case REMOVE: controller.removeWise(rq.getIntParam("id")); break;
            case MODIFY: controller.modifyWise(rq.getIntParam("id")); break;
            case BUILD: controller.buildJson(); break;
            case ERROR: controller.msg.printCommandIsNotExists(); break;
        }
    }
}
