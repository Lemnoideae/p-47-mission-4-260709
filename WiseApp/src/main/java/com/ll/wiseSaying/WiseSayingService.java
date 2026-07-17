package com.ll.wiseSaying;

public class WiseSayingService {
    public WiseSayingService(WiseSayingRepository repository) {
        this.repository = repository;
        this.sb = new StringBuilder();
    }
    final WiseSaying getWiseById(int id) { return repository.getWiseById(id); }
    boolean isWiseContains(int id) { return repository.isWiseContains(id); }

    int createWiseAndGetId(String new_content, String new_author) {
        int id = repository.getNewIdNum();
        repository.addWise(new WiseSaying(id, new_content, new_author));
        return id;
    }

    String getWiseList() {
        sb.setLength(0);
        for (WiseSaying wise : repository.getWiseMap().descendingMap().values())
            sb.append(wise.toString()).append("\n");
        return sb.toString();
    }

    void removeWise(int id) { repository.removeWise(id); }
    void modifyWise(int id, String new_content, String new_author) {
        repository.modifyWise(id, new_content, new_author);
    }
    void buildJson() {

    }

    private final StringBuilder sb;
    private final WiseSayingRepository repository;
}
