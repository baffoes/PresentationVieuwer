package org.example.Model.SlideContent;

public class ImageContent extends SlideContent {
    private final String src;

    public ImageContent(String src) {
        super(0L); // Afbeeldingen hebben geen inspringing
        this.src = src;
    }

    @Override
    public String getContent() {
        return src;
    }

}
