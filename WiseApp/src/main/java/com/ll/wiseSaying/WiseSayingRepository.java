package com.ll.wiseSaying;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WiseSayingRepository {
    private final TreeMap<Integer, WiseSaying> wiseMap;
    private Integer newIdNum;
    private final Path lastIdPath;
    private final Path dataPath;

    public WiseSayingRepository(Pattern jsonPattern, String lastIdPath,
                                String dataPath) throws IOException {
        this.lastIdPath = Path.of(lastIdPath);
        this.dataPath = Path.of(dataPath);
        checkDbPath();

        this.newIdNum = 1 + Integer.parseInt(Files.readString(this.lastIdPath));
        this.wiseMap = initMap(jsonPattern);
    }
    public int getNewIdNum() { return newIdNum; }
    public final WiseSaying getWiseById(int id) { return wiseMap.get(id); }
    public TreeMap<Integer, WiseSaying> getWiseMap() { return wiseMap; }

    public boolean doesMapContainWise(int id) { return wiseMap.containsKey(id); }
    public boolean isWiseMapEmpty() { return wiseMap.isEmpty(); }

    public void addWise(WiseSaying newWise) {
        wiseMap.put(newIdNum, newWise);
        newIdNum++;
    }
    public void removeWise(int id) {
        wiseMap.remove(id);
    }

    public void modifyWise(int id, String newContent, String newAuthor) {
        wiseMap.get(id).modifyWise(newContent, newAuthor);
    }
    public void buildJson(String jsonStr) throws IOException {
        Files.writeString(lastIdPath, wiseMap.lastKey().toString());
        Files.writeString(dataPath, jsonStr);
    }

    private void checkDbPath() throws FileNotFoundException {
        if (Files.notExists(lastIdPath))
            throw new FileNotFoundException(lastIdPath.toString());
        if (Files.notExists(dataPath))
            throw new FileNotFoundException(dataPath.toString());
    }
    private TreeMap<Integer, WiseSaying> initMap(Pattern jsonPattern)
            throws IOException {
        TreeMap<Integer, WiseSaying> map = new TreeMap<>();

        String jsonStr = Files.readString(this.dataPath);
        if (newIdNum == 1 || jsonStr.equals("[]")) return map;

        Matcher jsonMatcher = jsonPattern.matcher(jsonStr);
        while (jsonMatcher.find()) {
            final int id = Integer.parseInt(jsonMatcher.group(1));
            final String content = jsonMatcher.group(2);
            final String author = jsonMatcher.group(3);

            map.put(id, new WiseSaying(id, content, author));
        }
        return map;
    }
}
