package org.example.Model.SlideContent;

// Abstracte klasse voor polymorfisme
public abstract class SlideContent {
    private final Long indentation;
    private String content;

    public SlideContent(String content,Long indentation) {
        this.content = content;
        this.indentation = indentation;
    }

    public long getIndentation() {
        return indentation;
    }

    public String getContent(){
        return content;
    }
}




/*package org.example.Modal;

public class SlideContent {
    private String type;
    private String content;
    private Long indentation;
    private String src;

    public SlideContent(String type, String content, Long indentation, String src) {
        this.type = type;
        this.content = content;
        this.indentation = indentation;
        this.src = src;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public long getIndentation() {
        return indentation;
    }

    public String getSrc() {
        return src;
    }

}
*/