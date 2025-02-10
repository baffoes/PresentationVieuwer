package org.example.Modal;

import java.util.List;

public class Slide {
    private List<SlideContent> contents;

    public Slide(List<SlideContent> contents) {
        this.contents = contents;
    }

    public List<SlideContent> getContents() {
        return contents;
    }
}

