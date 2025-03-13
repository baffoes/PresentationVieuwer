package org.example.Model.SlideContent;

public class TextContent extends SlideContent {
    private final String text;

    public TextContent(String text, Long indentation) {
        super(indentation);
        this.text = text;
    }

    @Override
    public String getContent() {
        return text;
    }
}
