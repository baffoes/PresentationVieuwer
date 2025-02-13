package org.example.Modal;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class JsonLoader extends Fileloader {

    // Change to return a single Presentation
    public Presentation loadJson(String folderPath) {
        File folder = new File(folderPath);
        List<Slide> slideList = new ArrayList<>();
        String showTitle = "";

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Recursively load presentations from subdirectories (if necessary)
                        Presentation presentation = loadJson(file.getAbsolutePath());
                        if (presentation != null) {
                            slideList.addAll(presentation.getSlides());
                        }
                    } else if (file.getName().endsWith(".json")) {
                        // Load the individual presentation from the JSON file
                        Presentation presentation = parseFile(file.getAbsolutePath());
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
        }
        else {
            System.out.println("Provided path is not a valid directory.");
        }

        // Return a single Presentation object with the loaded slides
        return slideList.isEmpty() ? null : new Presentation(showTitle, slideList);
    }


    public Presentation parseFile(String filePath) {
        List<Slide> slideList = new ArrayList<>();
        JSONParser parser = new JSONParser();


        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
             String showTitle = (String) jsonObject.get("showtitle");
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

                    Path originalPath = Paths.get(filePath);
                    Path modifiedPath = originalPath.getParent();

                    String fileName = slideItem.get("src") != null ? slideItem.get("src").toString() : "";
                    String fileNameFinal = modifiedPath.toString() + fileName;
                    String src = fileNameFinal.replace("\\", "/");


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




