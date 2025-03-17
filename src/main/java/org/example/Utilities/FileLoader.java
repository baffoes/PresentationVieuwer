package org.example.Utilities;

import org.example.Model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    public String showTitle;

    public Presentation loadPresentation(String folderPath) {
        File folder = new File(folderPath);
        List<Slide> slideList = new ArrayList<>();

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Recursively load presentations from subdirectories
                        Presentation presentation = loadPresentation(file.getAbsolutePath());
                        if (presentation != null) {
                            slideList.addAll(presentation.getSlides());
                            // Only update showTitle if it's the first valid title
                            if (presentation.getShowTitle() != null && showTitle == null) {
                                showTitle = presentation.getShowTitle();
                            }
                        }
                    } else {
                        // Process individual files (not directories)
                        FileParser parser = getParserForFile(file);
                        if (parser != null) {
                            // Parse the file to get the presentation
                            Presentation presentation = parser.parseFile(file.getAbsolutePath());
                            if (presentation != null) {
                                slideList.addAll(presentation.getSlides());
                                if (showTitle == null) {
                                    showTitle = presentation.getShowTitle();
                                }
                            }
                        }
                    }
                }
            }
        }

        // Return a new Presentation only if slides were found
        return slideList.isEmpty() ? null : new Presentation(showTitle, slideList);
    }

    private FileParser getParserForFile(File file) {
        if (file.getName().endsWith(".json")) {
            return new JsonParser(); // Return the JSON parser for JSON files
        } else if (file.getName().endsWith(".xml")) {
            return new XmlParser(); // Return the XML parser for XML files
        }
        return null;  // Return null for unsupported file types
    }
}
