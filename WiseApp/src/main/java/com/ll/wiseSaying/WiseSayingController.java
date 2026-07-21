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
        String new_content = msg.printAndInputContent(scanner);
        String new_author = msg.printAndInputAuthor(scanner);
        int wise_id = service.createWiseAndGetId(new_content, new_author);
        msg.printAddCommandCompleted(wise_id);
    }
    public void showList(Rq rq) {
        PageDto<WiseSaying> page_dto = service.getPagedList(rq);
        if (rq.doesRequestContainsParam("keywordType") &&
            rq.doesRequestContainsParam("keyword"))
            msg.printSearchedKeyword(rq);
        msg.printListString(page_dto, service.getListString(page_dto.wise_list()));
    }
    public void removeWise(int id) {
        if (service.isWiseContains(id)) {
            service.removeWise(id);
            msg.printRemoveCommandCompleted(id);
        }
        else msg.printWiseIsNotExists(id);
    }
    public void modifyWise(int id) {
        if (!service.isWiseContains(id)) { msg.printWiseIsNotExists(id); return;}
        WiseSaying current_wise = service.getWiseById(id);

        String new_content =
                msg.printAndInputNewContent(current_wise.getContent(), scanner);
        String new_author =
                msg.printAndInputNewAuthor(current_wise.getAuthor(), scanner);

        service.modifyWise(id, new_content, new_author);
    }
    public void buildJson() throws IOException {
        service.buildJson(); msg.printJsonBuildCompleted();
    }

}
