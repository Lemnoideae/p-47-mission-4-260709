package com.ll.wiseSaying;

import java.io.IOException;
import java.util.Scanner;

public class WiseSayingController {
    private final Scanner scanner;
    private final WiseSayingService service;

    public final MessagePrinter msg;

    public Rq printAndInputRequest() {
        System.out.print("명령) ");
        String cmd = scanner.nextLine().trim();
        return new Rq(cmd);
    }
    public WiseSayingController(Scanner scanner, WiseSayingService service) {
        this.scanner = scanner;
        this.service = service;
        this.msg = new MessagePrinter();
    }

    // To Service
    public void addWise() {
        String newContent = msg.printAndInputContent(scanner);
        String newAuthor = msg.printAndInputAuthor(scanner);
        int wiseId = service.createWiseAndGetId(newContent, newAuthor);
        msg.printAddCommandCompleted(wiseId);
    }
    public void showList(Rq rq) {
        PageDto<WiseSaying> pageDto = service.getPagedList(rq);
        if (rq.doesRequestContainsParam("keywordType") &&
            rq.doesRequestContainsParam("keyword"))
            msg.printSearchedKeyword(rq);

        msg.printListString(pageDto, service.getListString(pageDto.wiseList()));
    }
    public void removeWise(int id) {
        if (service.doesMapContainWise(id)) {
            service.removeWise(id);
            msg.printRemoveCommandCompleted(id);
        }
        else msg.printWiseIsNotExists(id);
    }
    public void modifyWise(int id) {
        if (!service.doesMapContainWise(id)) { msg.printWiseIsNotExists(id); return;}
        WiseSaying currentWise = service.getWiseById(id);

        String newContent =
                msg.printAndInputNewContent(currentWise.getContent(), scanner);
        String newAuthor =
                msg.printAndInputNewAuthor(currentWise.getAuthor(), scanner);

        service.modifyWise(id, newContent, newAuthor);
    }
    public void buildJson() throws IOException {
        service.buildJson();
        msg.printJsonBuildCompleted();
    }

}
