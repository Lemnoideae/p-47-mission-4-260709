package com.ll.wiseSaying;

import java.io.IOException;

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
    void buildJson() throws IOException {
        if (repository.isWiseMapEmpty()) {
            repository.buildJson("[]");
            return;
        }

        sb.setLength(0);
        int idx = 0;
        int size = repository.getWiseMap().size();

        sb.append("[\n");
        for (WiseSaying wise : repository.getWiseMap().values()) {
            final String id = Integer.toString(wise.getId());
            final String content = wise.getContent();
            final String author = wise.getAuthor();

            sb.append("  {\n");
            sb.append("    \"id\": ").append(id).append(",\n");
            sb.append("    \"content\": \"").append(content).append("\",\n");
            sb.append("    \"author\": \"").append(author).append("\"\n");
            sb.append("  }");

            if (idx++ < size - 1) sb.append(",\n");
        }
        sb.append("\n]");

        repository.buildJson(sb.toString());
    }

    private final StringBuilder sb;
    private final WiseSayingRepository repository;
}
