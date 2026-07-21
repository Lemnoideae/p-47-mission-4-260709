package com.ll.wiseSaying;

import java.util.List;

public record PageDto<WiseSaying>(List<WiseSaying> wiseList,
                                  int currentPages, int maxPages) {}