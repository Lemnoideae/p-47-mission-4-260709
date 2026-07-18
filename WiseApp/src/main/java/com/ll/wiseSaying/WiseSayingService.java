package com.ll.wiseSaying;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class WiseSayingService {
    public WiseSayingService(WiseSayingRepository repository) {
        this.repository = repository;
        this.sb = new StringBuilder();
        this.WISES_PER_PAGE = 5;
    }
    record PageDto<WiseSaying>(List<WiseSaying> wise_list,
                                      int current_pages, int max_pages) {}

    final WiseSaying getWiseById(int id) { return repository.getWiseById(id); }
    boolean isWiseContains(int id) { return repository.isWiseContains(id); }

    int createWiseAndGetId(String new_content, String new_author) {
        int id = repository.getNewIdNum();
        repository.addWise(new WiseSaying(id, new_content, new_author));
        return id;
    }

    PageDto<WiseSaying> getPagedList(Command cmd) {
        Predicate<WiseSaying> filter_condition = switch (cmd.keyword_type) {
            case "content" -> wise -> wise.getContent().contains(cmd.keyword);
            case "author" -> wise -> wise.getAuthor().contains(cmd.keyword);
            default -> _ -> true;
        };
        List<WiseSaying> filtered_list = repository.getWiseMap().descendingMap().values()
                        .stream().filter(filter_condition).toList();

        final int total_wise = filtered_list.size();
        final int current_pages = cmd.page_number;
        final int max_pages = updateMaxPages(total_wise);

        List<WiseSaying> paged_list = filtered_list.stream()
                .skip((long) (current_pages - 1) * WISES_PER_PAGE)
                .limit(WISES_PER_PAGE)
                .toList();

        return new PageDto<>(paged_list, current_pages, max_pages);
    }
    String getListString(List<WiseSaying> wise_list) {
        sb.setLength(0);
        for (WiseSaying wise : wise_list) sb.append(wise).append("\n");
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

    // Private
    private int updateMaxPages(int total_wises) {
        return Math.max(1, (int) Math.ceil((double) total_wises / WISES_PER_PAGE));
    }
    private final int WISES_PER_PAGE;
    private final StringBuilder sb;
    private final WiseSayingRepository repository;
}
