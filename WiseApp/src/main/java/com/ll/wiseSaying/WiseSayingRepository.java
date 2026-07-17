package com.ll.wiseSaying;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WiseSayingRepository {
    public WiseSayingRepository(String last_id_path, String data_path)
            throws IOException {
        this.last_id_path = Path.of(last_id_path);
        this.data_path = Path.of(data_path);
        checkDbPath();

        this.new_id_num = 1 + Integer.parseInt(Files.readString(this.last_id_path));
        String json_str = Files.readString(this.data_path);

        if (new_id_num == 1 || json_str.equals("[]"))
            this.wise_map = new TreeMap<>();
        else
            this.wise_map = initMap(json_str);
    }
    public int getNewIdNum() { return new_id_num; }
    public boolean isWiseContains(int id) { return wise_map.containsKey(id); }
    public boolean isWiseMapEmpty() { return wise_map.isEmpty(); }
    public TreeMap<Integer, WiseSaying> getWiseMap() { return wise_map; }
    public final WiseSaying getWiseById(int id) { return wise_map.get(id); }

    public void addWise(WiseSaying new_wise) {
        wise_map.put(new_id_num, new_wise);
        new_id_num++;
    }
    public void removeWise(int id) { wise_map.remove(id); }

    public void modifyWise(int id, String new_content, String new_author) {
        wise_map.get(id).modifyWise(new_content, new_author);
    }
    public void buildJson(String json_str) throws IOException {
        Files.writeString(last_id_path, wise_map.lastKey().toString());
        Files.writeString(data_path, json_str);
    }

    // Private :
    private void checkDbPath() throws FileNotFoundException {
        if (Files.notExists(last_id_path))
            throw new FileNotFoundException(last_id_path.toString());
        if (Files.notExists(data_path))
            throw new FileNotFoundException(data_path.toString());
    }
    private TreeMap<Integer, WiseSaying> initMap(String json_str) {
        String regex_for_parse = "\\{" +
                "\\s*\"id\"\\s*:\\s*(\\d+)\\s*," +
                "\\s*\"content\"\\s*:\\s*\"([^\"]*)\"\\s*," +
                "\\s*\"author\"\\s*:\\s*\"([^\"]*)\"\\s*" +
                "}";
        Pattern obj_ptn = Pattern.compile(regex_for_parse);
        Matcher obj_matcher = obj_ptn.matcher(json_str);

        TreeMap<Integer, WiseSaying> map = new TreeMap<>();
        while (obj_matcher.find()) {
            final int id = Integer.parseInt(obj_matcher.group(1));
            final String content = obj_matcher.group(2);
            final String author = obj_matcher.group(3);

            map.put(id, new WiseSaying(id, content, author));
        }
        return map;
    }
    private final Path last_id_path;
    private final Path data_path;
    private Integer new_id_num;
    private final TreeMap<Integer, WiseSaying> wise_map;

}
