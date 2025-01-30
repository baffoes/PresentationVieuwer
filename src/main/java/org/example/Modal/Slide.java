package org.example.Modal;

public class Slide {
    private String type;
    private String content;
    private long indentation;
    private String src;

    public Slide(String type, String content, long indentation, String src) {
        this.type = type;
        this.content = content;
        this.indentation = indentation;
        this.src = src;
    }
}
