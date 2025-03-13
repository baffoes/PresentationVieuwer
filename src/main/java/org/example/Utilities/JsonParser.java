package org.example.Utilities;

import org.example.Model.Presentation;
import org.example.Model.Slide;
import org.example.Model.SlideContent.ImageContent;
import org.example.Model.SlideContent.SlideContent;
import org.example.Model.SlideContent.TextContent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonParser extends FileLoader {


    public Presentation parseFile(String filePath) {
        List<Slide> slideList = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            String showTitle = (String) jsonObject.get("showtitle");

            JSONArray slides = (JSONArray) jsonObject.get("slides");
            for (Object slideObj : slides) {
                JSONArray slideItems = (JSONArray) slideObj;
                List<SlideContent> slideContents = new ArrayList<>();

                for (Object obj : slideItems) {
                    JSONObject slideItem = (JSONObject) obj;
                    Long indentation = slideItem.get("indentation") != null ? (Long) slideItem.get("indentation") : 0;

                    // Verwerken van tekstuele inhoud
                    if (slideItem.containsKey("content")) {
                        String content = (String) slideItem.get("content");
                        slideContents.add(new TextContent(content, indentation));
                    }
                    // Verwerken van afbeeldingen
                    else if (slideItem.containsKey("src")) {
                        String src = slideItem.get("src").toString();
                        Path originalPath = Paths.get(filePath);
                        Path modifiedPath = originalPath.getParent();
                        String fileNameFinal = modifiedPath.toString() + "/" + src;
                        String srcFinal = fileNameFinal.replace("\\", "/");

                        slideContents.add(new ImageContent(srcFinal));
                    }
                }

                // Maak een nieuwe Slide aan met de inhoud
                slideList.add(new Slide(slideContents));
            }

            return new Presentation(showTitle, slideList);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Error parsing file: " + filePath);
        }

        return null;  // Retourneer null bij een fout
    }
}