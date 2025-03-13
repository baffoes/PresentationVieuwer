package org.example.Utilities;

import org.example.Model.Presentation;
import org.example.Model.Slide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

public String showTitle;

    public Presentation load(String folderPath) {
        File folder = new File(folderPath);
        List<Slide> slideList = new ArrayList<>();

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        Presentation presentation = load(file.getAbsolutePath());
                        if (presentation != null) {
                            slideList.addAll(presentation.getSlides());
                        }
                    } else {
                        loadPresentation(file, slideList);
                    }
                }
            }
        }

        return slideList.isEmpty() ? null : new Presentation(showTitle, slideList);
    }

    private void loadPresentation(File file, List<Slide> slideList) {
        if (file.getName().endsWith(".json")) {
            JsonParser jsonParser = new JsonParser();
            Presentation presentation = jsonParser.parseFile(file.getAbsolutePath());
            if (presentation != null) {
                slideList.addAll(presentation.getSlides());
                showTitle = presentation.getShowTitle();
            }
        } else if (file.getName().endsWith(".xml")) {
            XmlParser xmlParser = new XmlParser();
            Presentation presentation = xmlParser.parseFile(file.getAbsolutePath());
            if (presentation != null) {
                slideList.addAll(presentation.getSlides());
                showTitle = presentation.getShowTitle();
            }
        }
    }
}
