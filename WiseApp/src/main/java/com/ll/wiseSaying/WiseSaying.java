package com.ll.wiseSaying;

public class WiseSaying implements Comparable<WiseSaying> {
    // public
    public WiseSaying(final int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }
    @Override
    public String toString() { return id + " / " + author + " / " + content; }
    @Override
    public int compareTo(WiseSaying w) { return this.id - w.id; }

    public void modifyWise(String new_content, String new_author) {
        this.content = new_content;
        this.author = new_author;
    }

    public int getId() { return id; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }

    // private
    private final int id;
    private String content;
    private String author;

}
