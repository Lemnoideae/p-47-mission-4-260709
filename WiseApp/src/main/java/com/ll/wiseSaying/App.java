package com.ll.wiseSaying;

import java.io.IOException;
import java.util.Scanner;

public class App {
    private final WiseSayingController controller;
    private boolean isAppWorking;

    public void run() throws IOException {
        controller.msg.printEntranceMessage();
        while (isAppWorking) loopStart();
    }
    public App(Scanner scanner) throws IOException {
        isAppWorking = true;
        this.controller = new WiseSayingController(scanner,
                new WiseSayingService(new WiseSayingRepository(
                        new RegexPatterns().jsonPattern,
                        "db/wiseSaying/lastId.txt",
                        "db/wiseSaying/data.json")));
    }

    private void loopStart() throws IOException {
        final Rq rq = controller.printAndInputRequest();
        switch (rq.getCmd()) {
            case EXIT: isAppWorking = false; return;
            case ADD: controller.addWise(); break;
            case SHOW: controller.showList(rq); break;
            case REMOVE: controller.removeWise(rq.getIntParam("id")); break;
            case MODIFY: controller.modifyWise(rq.getIntParam("id")); break;
            case BUILD: controller.buildJson(); break;
            case ERROR: controller.msg.printCommandIsNotExists(); break;
        }
    }
}
