package org.example.Modal;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;


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

    

}
