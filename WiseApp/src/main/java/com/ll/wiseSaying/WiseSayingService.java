package com.ll.wiseSaying;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class WiseSayingService {
    WiseSayingService(Path data_path) throws IOException {
        String regex_for_parse = "\\{" +
                "\\s*\"id\"\\s*:\\s*(\\d+)\\s*," +
                "\\s*\"content\"\\s*:\\s*\"([^\"]*)\"\\s*," +
                "\\s*\"author\"\\s*:\\s*\"([^\"]*)\"\\s*" +
                "}";
        Pattern regex_pattern = Pattern.compile(regex_for_parse);
        this.json_matcher = regex_pattern.matcher(Files.readString(data_path));
        this.sb = new StringBuilder();
    }

    TreeMap<Integer, WiseSaying> initList(Path lastId_path) throws IOException {
        int lastId = Integer.parseInt(Files.readString(lastId_path));

        TreeMap<Integer, WiseSaying> wise_map;
        if (lastId == 0) wise_map = new TreeMap<>();
        else wise_map = parseJsonToList();

        return wise_map;
    }

    void buildList(WiseSayingController controller,
                   TreeMap<Integer, WiseSaying> wise_map,
                   DbPath db_path) throws IOException {
        String lastId_str = Integer.toString(wise_map.lastEntry().getValue().getId());
        String json_str = writeObjectToJson(wise_map);

        Files.write(db_path.last_id, lastId_str.getBytes());
        Files.writeString(db_path.data, json_str);

        controller.printBuildCompleted();
    }

    String getListString(TreeMap<Integer, WiseSaying> wise_map) {
        sb.setLength(0);
        sb.append("번호 / 작가 / 명언").append("\n")
                .append("----------------------").append("\n");
        for (Map.Entry<Integer, WiseSaying> entry : wise_map.descendingMap().entrySet()) {
            sb.append(entry.getValue().toString()).append("\n");
        }
        return sb.toString();
    }

    void addNewWise(Scanner scanner, WiseSayingController controller,
                    Map<Integer, WiseSaying> wise_map, final int id) {
        String new_wise_content = controller.printAndInputContent(scanner);
        String new_wise_author = controller.printAndInputAuthor(scanner);

        wise_map.put(id, new WiseSaying(id, new_wise_content, new_wise_author));
        controller.printAddCommandCompleted(id);
    }
    void modifyWise(Scanner scanner, WiseSayingController controller,
                    TreeMap<Integer, WiseSaying> wise_map, final int id) {
        if (!wise_map.containsKey(id)) {
            controller.printIdWiseIsNotExisted(id);
            return;
        }
        WiseSaying current_wise = wise_map.get(id);

        controller.printPrevContent(current_wise.getContent());
        String new_content = controller.printAndInputContent(scanner);

        controller.printPrevAuthor(current_wise.getAuthor());
        String new_author = controller.printAndInputAuthor(scanner);

        current_wise.modifyWise(new_content, new_author);
    }
    void deleteWise(WiseSayingController controller,
                    TreeMap<Integer, WiseSaying> wise_map, final int id) {
        if (!wise_map.containsKey(id)) {
            controller.printIdWiseIsNotExisted(id);
            return;
        }
        wise_map.remove(id);
        controller.printRemoveCommandCompleted(id);
    }

    TreeMap<Integer, WiseSaying> parseJsonToList() {
        TreeMap<Integer, WiseSaying> wise_map = new TreeMap<>();
        while (json_matcher.find()) {
            final int id = Integer.parseInt(json_matcher.group(1));
            String content = json_matcher.group(2);
            String author = json_matcher.group(3);

            wise_map.put(id, new WiseSaying(id, content, author));
        }
        return wise_map;
    }

    String writeObjectToJson(TreeMap<Integer, WiseSaying> wise_map) {
        if (wise_map.isEmpty()) return "[]";
        sb.setLength(0);
        sb.append("[\n");

        int idx = 0;
        int size = wise_map.size();
        for (Map.Entry<Integer, WiseSaying> entry : wise_map.entrySet()) {
            WiseSaying current_wise = entry.getValue();

            final String id = String.valueOf(current_wise.getId());
            final String content = current_wise.getContent();
            final String author = current_wise.getAuthor();

            sb.append("  {\n");
            sb.append("    \"id\": ").append(id).append(",\n");
            sb.append("    \"content\": \"").append(content).append("\",\n");
            sb.append("    \"author\": \"").append(author).append("\"\n");
            sb.append("  }");

            if (idx++ < size - 1) sb.append(",\n");
        }
        sb.append("\n]");
        return sb.toString();
    }

    private final Matcher json_matcher;
    private final StringBuilder sb;
}
