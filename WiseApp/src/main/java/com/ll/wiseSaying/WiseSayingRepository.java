package com.ll.wiseSaying;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.TreeMap;

class WiseSayingRepository {
    WiseSayingRepository(String lastId_path, String data_path) throws IOException {
        this.db_path = new DbPath(lastId_path, data_path);
        this.service = new WiseSayingService(this.db_path.data);
        this.wise_map = service.initList(this.db_path.last_id);
        this.new_id_num = 1 + Integer.parseInt(Files.readString(this.db_path.last_id));
    }
    void addNewWise(Scanner scanner, WiseSayingController controller) {
        service.addNewWise(scanner, controller, wise_map, new_id_num);
        this.new_id_num++;
    }
    String getListString() { return service.getListString(wise_map); }

    void buildList(WiseSayingController controller) throws IOException {
        service.buildList(controller, wise_map, db_path);
    }
    void modifyWise(Scanner scanner, WiseSayingController controller, final int id) {
        service.modifyWise(scanner, controller, wise_map, id);
    }
    void deleteWise(WiseSayingController controller, final int id) {
        service.deleteWise(controller, wise_map, id);
    }

    private final DbPath db_path;
    private final WiseSayingService service;
    private final TreeMap<Integer, WiseSaying> wise_map;
    private Integer new_id_num;
}
