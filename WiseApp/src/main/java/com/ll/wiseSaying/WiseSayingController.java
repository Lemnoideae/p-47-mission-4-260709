package com.ll.wiseSaying;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class WiseSayingController {
    private final RegexPatterns regex_ptns;
    private final Scanner scanner;
    private final WiseSayingService service;

    public final MessagePrinter msg;

    public Command printAndInputCommand() {
        System.out.print("명령) ");
        String cmd = scanner.nextLine().trim();
        switch (cmd) {
            case "종료": return new Command(CommandId.EXIT);
            case "등록": return new Command(CommandId.ADD);
            case "목록": return new Command(CommandId.SHOW);
            case "빌드": return new Command(CommandId.BUILD);
        }
        Matcher cmd_mth;
        cmd_mth = regex_ptns.show_cmd_with_pages.matcher(cmd);
        if (cmd_mth.find())
            return new Command(CommandId.SHOW, -1,
                    Integer.parseInt( cmd_mth.group(1) ));

        cmd_mth = regex_ptns.search_cmd.matcher(cmd);
        if (cmd_mth.find())
            return new Command(CommandId.SHOW, cmd_mth.group(1),  cmd_mth.group(2));

        cmd_mth = regex_ptns.remove_cmd.matcher(cmd);
        if (cmd_mth.find())
            return new Command(CommandId.REMOVE, Integer.parseInt(cmd_mth.group(1)));

        cmd_mth = regex_ptns.modify_cmd.matcher(cmd);
        if (cmd_mth.find())
            return new Command(CommandId.MODIFY, Integer.parseInt(cmd_mth.group(1)));

        return new Command(CommandId.ERROR);
    }
    public WiseSayingController(Scanner scanner, WiseSayingService service) {
        this.scanner = scanner;
        this.service = service;
        this.msg = new MessagePrinter();
        this.regex_ptns = new RegexPatterns();
    }

    // To Service
    public void addWise() {
        String new_content = msg.printAndInputContent(scanner);
        String new_author = msg.printAndInputAuthor(scanner);
        int wise_id = service.createWiseAndGetId(new_content, new_author);
        msg.printAddCommandCompleted(wise_id);
    }
    public void showList(Command cmd) {
        PageDto<WiseSaying> page_dto = service.getPagedList(cmd);
        if (cmd.isAppMustSearchWiseByKeyword()) msg.printSearchedKeyword(cmd);
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
