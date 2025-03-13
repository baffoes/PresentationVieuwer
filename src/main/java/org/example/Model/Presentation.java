package org.example.Model;

import java.util.List;


public class Presentation {
    private String showTitle;
    private List<Slide> slides;

    public Presentation(String showTitle, List<Slide> slides) {
        this.showTitle = showTitle;
        this.slides = slides;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public List<Slide> getSlides() {
        return slides;
    }
}

