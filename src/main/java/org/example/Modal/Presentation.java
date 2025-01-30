package org.example.Modal;

import javax.swing.*;
import java.util.List;

public class Presentation {
    private String title;
    private List<Slide> slides;
    private int currentIndex;

    public Presentation(String title, List<Slide> slides, int currentIndex) {
        this.title = title;
        this.slides = slides;
        this.currentIndex = currentIndex;
    }

    public String getTitle() {
        return title;
    }

    public List<Slide> getSlides() {
        return slides;
    }
}
