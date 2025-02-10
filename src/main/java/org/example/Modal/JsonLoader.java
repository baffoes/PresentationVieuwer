package org.example.Modal;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class JsonLoader extends Fileloader {

    public String loadJson(String folderPath) {
        StringBuilder result = new StringBuilder();
        File folder = new File(folderPath);

        // Debug: Print the folder path
        System.out.println("Checking folder: " + folderPath);

        if (folder.isDirectory()) {
            // List all files and directories in the current folder
            File[] files = folder.listFiles();

            // If the folder is not empty
            if (files != null) {
                for (File file : files) {
                    // If the file is a directory, recurse into it
                    if (file.isDirectory()) {
                        System.out.println("Entering directory: " + file.getName());
                        // Recursively search the subdirectory
                        result.append(loadJson(file.getAbsolutePath()));
                    } else if (file.getName().endsWith(".json")) {
                        // If the file is a JSON file, parse it and stop searching
                        System.out.println("Found JSON file: " + file.getName());
                        String parsedContent = parseFile(file.getAbsolutePath());
                        result.append(parsedContent).append("\n");

                        // Return the result and stop further search
                        return result.toString();  // Stops searching once the JSON file is found
                    }
                }
            }
        } else {
            result.append("Provided path is not a valid directory.\n");
        }

        return result.toString();
    }

    public String parseFile(String filePath) {
        StringBuilder result = new StringBuilder();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            // Parse the JSON file
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            // Get the showtitle
            String showTitle = (String) jsonObject.get("showtitle");
            result.append("Show Title: ").append(showTitle).append("\n");

            // Get the slides array
            JSONArray slides = (JSONArray) jsonObject.get("slides");

            // Iterate through each slide
            for (int i = 0; i < slides.size(); i++) {
                JSONArray slide = (JSONArray) slides.get(i);
                result.append("Slide ").append(i + 1).append(":\n");

                // Iterate through each item in the slide
                for (Object obj : slide) {
                    JSONObject slideItem = (JSONObject) obj;
                    String type = (String) slideItem.get("type");
                    result.append("  Type: ").append(type).append("\n");

                    // Process based on the type
                    if ("title".equals(type)) {
                        String content = (String) slideItem.get("content");
                        result.append("    Title Content: ").append(content).append("\n");
                    } else if ("text".equals(type)) {
                        String content = (String) slideItem.get("content");
                        long indentation = (long) slideItem.get("indentation");
                        result.append("    Text Content: ").append(content)
                                .append(" (Indentation: ").append(indentation).append(")\n");
                    } else if ("image".equals(type)) {
                        String src = (String) slideItem.get("src");
                        result.append("    Image Source: ").append(src).append("\n");
                    }
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            result.append("Error reading JSON file: ").append(e.getMessage()).append("\n");
        }

        return result.toString();
    }

    // Change to return a single Presentation
    public Presentation loadJson2(String folderPath) {
        File folder = new File(folderPath);
        List<Slide> slideList = new ArrayList<>();
        String showTitle = "";

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Recursively load presentations from subdirectories (if necessary)
                        Presentation presentation = loadJson2(file.getAbsolutePath());
                        if (presentation != null) {
                            slideList.addAll(presentation.getSlides());
                        }
                    } else if (file.getName().endsWith(".json")) {
                        // Load the individual presentation from the JSON file
                        Presentation presentation = parseFile2(file.getAbsolutePath());
                        if (presentation != null) {
                            // Only set the showTitle from the first JSON file
                            if (showTitle.isEmpty()) {
                                showTitle = presentation.getShowTitle();
                            }
                            slideList.addAll(presentation.getSlides());
                        }
                    }
                }
            }
        } else {
            System.out.println("Provided path is not a valid directory.");
        }

        // Return a single Presentation object with the loaded slides
        return slideList.isEmpty() ? null : new Presentation(showTitle, slideList);
    }


    public Presentation parseFile2(String filePath) {
        List<Slide> slideList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        String showTitle = "";

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            showTitle = (String) jsonObject.get("showtitle");
            System.out.println("Show Title: " + showTitle);

            JSONArray slides = (JSONArray) jsonObject.get("slides");

            for (int i = 0; i < slides.size(); i++) {
                JSONArray slideItems = (JSONArray) slides.get(i);
                List<SlideContent> slideContents = new ArrayList<>();

                for (Object obj : slideItems) {
                    JSONObject slideItem = (JSONObject) obj;
                    String type = (String) slideItem.get("type");
                    String content = (String) slideItem.get("content");
                    Long indentation = slideItem.get("indentation") != null ? (Long) slideItem.get("indentation") : 0;
                    String src = (String) slideItem.get("src");

                    // Create SlideContent object
                    SlideContent slideContent = new SlideContent(type, content, indentation, src);
                    slideContents.add(slideContent);
                }

                // Create Slide object and add it to the slideList
                Slide slide = new Slide(slideContents);
                slideList.add(slide);
            }

            // Return the created Presentation object with the showTitle and slideList
            return new Presentation(showTitle, slideList);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;  // Return null if an error occurs
    }

    public void displayPresentation(Presentation presentation) {
        if (presentation == null) {
            System.out.println("No presentation to display.");
            return;
        }

        System.out.println("Presentation Title: " + presentation.getShowTitle());
        System.out.println("Number of Slides: " + presentation.getSlides().size());

        for (Slide slide : presentation.getSlides()) {
            System.out.println("\n--- Slide ---");
            List<SlideContent> slideContents = slide.getContents();
            for (SlideContent content : slideContents) {
                System.out.println("Type: " + content.getType());
                System.out.println("Content: " + content.getContent());
                System.out.println("Indentation: " + content.getIndentation());
                if (content.getSrc() != null) {
                    System.out.println("Source: " + content.getSrc());
                }
                System.out.println();
            }
        }
    }
}




