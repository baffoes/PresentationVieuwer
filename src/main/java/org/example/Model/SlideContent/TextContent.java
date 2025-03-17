package org.example.Model.SlideContent;

public class TextContent extends SlideContent {
    private String font;

    // Constructor that includes font information
    public TextContent(String content, Long indentation, String font) {
        super(content, indentation);  // Pass content and indentation to the base class
        this.font = font != null ? font : "Arial";  // Default font to Arial if null
    }

    public String getFont() {
        return font;
    }
}
