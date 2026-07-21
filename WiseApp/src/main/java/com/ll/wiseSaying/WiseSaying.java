package com.ll.wiseSaying;

public class WiseSaying implements Comparable<WiseSaying> {
    private final int id;
    private String content;
    private String author;

    public WiseSaying(final int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }
    public int getId() { return id; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }

    @Override
    public String toString() { return id + " / " + author + " / " + content; }
    @Override
    public int compareTo(WiseSaying w) { return this.id - w.id; }

    public void modifyWise(String newContent, String newAuthor) {
        this.content = newContent;
        this.author = newAuthor;
    }
}
