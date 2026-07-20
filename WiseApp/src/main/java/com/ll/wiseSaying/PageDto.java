package com.ll.wiseSaying;

import java.util.List;

public record PageDto<WiseSaying>(List<WiseSaying> wise_list,
                           int current_pages, int max_pages) {}