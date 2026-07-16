package com.ll.wiseSaying;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

class DbPath {
    DbPath(String last_id, String data) throws FileNotFoundException {
        this.last_id = Path.of(last_id);
        this.data = Path.of(data);
        this.checkPaths();
    }

    final Path last_id;
    final Path data;

    private void checkPaths() throws FileNotFoundException {
        String absolutePath;
        if (Files.notExists(this.data)) {
            absolutePath = this.data.toAbsolutePath().toString();
            throw new FileNotFoundException("Data file not found : " + absolutePath);
        }
        if (Files.notExists(this.last_id)) {
            absolutePath = this.last_id.toAbsolutePath().toString();
            throw new FileNotFoundException("Last Id file not found : " + absolutePath);
        }
    }

}
