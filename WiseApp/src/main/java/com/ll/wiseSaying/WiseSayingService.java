package com.ll.wiseSaying;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class WiseSayingService {
    private final WiseSayingRepository repository;
    private final StringBuilder sb;
    private final int WISES_PER_PAGE;

    public WiseSayingService(WiseSayingRepository repository) {
        this.repository = repository;
        this.sb = new StringBuilder();
        this.WISES_PER_PAGE = 5;
    }

    final WiseSaying getWiseById(int id) { return repository.getWiseById(id); }
    boolean isWiseContains(int id) { return repository.isWiseContains(id); }

    int createWiseAndGetId(String newContent, String newAuthor) {
        int id = repository.getNewIdNum();
        repository.addWise(new WiseSaying(id, newContent, newAuthor));
        return id;
    }

    PageDto<WiseSaying> getPagedList(Rq rq) {
        String keyword = rq.getStringParam("keyword");
        Predicate<WiseSaying> filterCondition =
                switch (rq.getStringParam("keywordType")) {
            case "content" -> wise -> wise.getContent().contains(keyword);
            case "author" -> wise -> wise.getAuthor().contains(keyword);
            default -> _ -> true;
        };
        List<WiseSaying> filtered_list = repository.getWiseMap().descendingMap().values()
                        .stream().filter(filterCondition).toList();

        final int totalWise = filtered_list.size();
        final int currentPages = rq.getIntParam("page");
        final int maxPages = updateMaxPages(totalWise);

        List<WiseSaying> pagedList = filtered_list.stream()
                .skip((long) (currentPages - 1) * WISES_PER_PAGE)
                .limit(WISES_PER_PAGE)
                .toList();

        return new PageDto<>(pagedList, currentPages, maxPages);
    }
    String getListString(List<WiseSaying> wise_list) {
        sb.setLength(0);
        for (WiseSaying wise : wise_list) sb.append(wise).append("\n");
        return sb.toString();
    }

    void removeWise(int id) { repository.removeWise(id); }
    void modifyWise(int id, String newContent, String newAuthor) {
        repository.modifyWise(id, newContent, newAuthor);
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

    private int updateMaxPages(int totalWises) {
        return Math.max(1, (int) Math.ceil((double) totalWises / WISES_PER_PAGE));
    }
}
