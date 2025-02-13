package org.example.Modal;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.nio.file.*;
import java.util.*;

public class XmlParser extends Fileloader {

    public Presentation parseFile(String filePath) {
        List<Slide> slideList = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);

            // Extract the showtitle
            NodeList showtitleNodeList = document.getElementsByTagName("showtitle");
            String showTitle = showtitleNodeList.getLength() > 0 ? showtitleNodeList.item(0).getTextContent() : "";
            System.out.println("Show Title: " + showTitle);

            // Extract slide information
            NodeList slideNodeList = document.getElementsByTagName("slide");
            for (int i = 0; i < slideNodeList.getLength(); i++) {
                Node slideNode = slideNodeList.item(i);
                List<SlideContent> slideContents = new ArrayList<>();

                // Extract title of the slide
                NodeList titleNodeList = ((Element) slideNode).getElementsByTagName("title");
                if (titleNodeList.getLength() > 0) {
                    String titleText = titleNodeList.item(0).getTextContent();
                    String font = ((Element) titleNodeList.item(0)).getAttribute("font");
                    slideContents.add(new SlideContent("title", titleText, 0L, font));
                }

                // Extract text elements
                NodeList textNodeList = ((Element) slideNode).getElementsByTagName("text");
                for (int j = 0; j < textNodeList.getLength(); j++) {
                    Node textNode = textNodeList.item(j);
                    String textContent = textNode.getTextContent();
                    Long indentation = ((Element) textNode).getAttribute("indentation") != null ? Long.parseLong(((Element) textNode).getAttribute("indentation")) : 0L;
                    String font = ((Element) textNode).getAttribute("font");
                    slideContents.add(new SlideContent("text", textContent, indentation, font));
                }

                // Extract image elements
                NodeList imageNodeList = ((Element) slideNode).getElementsByTagName("image");
                for (int j = 0; j < imageNodeList.getLength(); j++) {
                    Node imageNode = imageNodeList.item(j);
                    String src = ((Element) imageNode).getAttribute("src");

                    // Handling the image path
                    Path originalPath = Paths.get(filePath);
                    Path modifiedPath = originalPath.getParent();
                    String fileNameFinal = modifiedPath.toString() + src;
                    String srcFinal = fileNameFinal.replace("\\", "/");

                    slideContents.add(new SlideContent("image", "", 0L, srcFinal));
                }

                // Create Slide object and add it to the slideList
                Slide slide = new Slide(slideContents);
                slideList.add(slide);
            }

            // Return the created Presentation object with the showTitle and slideList
            return new Presentation(showTitle, slideList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;  // Return null if an error occurs
    }

    public void parseTest(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);

            // Extract the showtitle
            NodeList showtitleNodeList = document.getElementsByTagName("showtitle");
            if (showtitleNodeList.getLength() > 0) {
                System.out.println("Show Title: " + showtitleNodeList.item(0).getTextContent());
            }

            // Extract slide information
            NodeList slideNodeList = document.getElementsByTagName("slide");
            for (int i = 0; i < slideNodeList.getLength(); i++) {
                Node slideNode = slideNodeList.item(i);
                System.out.println("\nSlide " + (i + 1));

                // Extract title of the slide
                NodeList titleNodeList = ((Element) slideNode).getElementsByTagName("title");
                if (titleNodeList.getLength() > 0) {
                    String titleText = titleNodeList.item(0).getTextContent();
                    String font = ((Element) titleNodeList.item(0)).getAttribute("font");
                    System.out.println("Title: " + titleText + " (Font: " + font + ")");
                }

                // Extract text elements
                NodeList textNodeList = ((Element) slideNode).getElementsByTagName("text");
                for (int j = 0; j < textNodeList.getLength(); j++) {
                    Node textNode = textNodeList.item(j);
                    String textContent = textNode.getTextContent();
                    String indentation = ((Element) textNode).getAttribute("indentation");
                    String font = ((Element) textNode).getAttribute("font");
                    System.out.println("Text (Indentation: " + indentation + ", Font: " + font + "): " + textContent);
                }

                // Extract image elements
                NodeList imageNodeList = ((Element) slideNode).getElementsByTagName("image");
                for (int j = 0; j < imageNodeList.getLength(); j++) {
                    Node imageNode = imageNodeList.item(j);
                    String src = ((Element) imageNode).getAttribute("src");
                    System.out.println("Image: " + src);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
