package org.example.Utilities;

import org.example.Model.SlideContent.*;
import org.example.Model.*;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.nio.file.*;
import java.util.*;

public class XmlParser extends FileParser    {

    @Override
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
                    slideContents.add(new TitleContent(titleText));
                }

                // Extract text elements
                NodeList textNodeList = ((Element) slideNode).getElementsByTagName("text");
                for (int j = 0; j < textNodeList.getLength(); j++) {
                    Node textNode = textNodeList.item(j);
                    String textContent = textNode.getTextContent();
                    Long indentation = ((Element) textNode).hasAttribute("indentation") ?
                            Long.parseLong(((Element) textNode).getAttribute("indentation")) : 0L;
                    String font = ((Element) textNode).getAttribute("font");
                    slideContents.add(new TextContent(textContent, indentation,font));
                }

                // Extract image elements
                NodeList imageNodeList = ((Element) slideNode).getElementsByTagName("image");
                for (int j = 0; j < imageNodeList.getLength(); j++) {
                    Node imageNode = imageNodeList.item(j);
                    String src = ((Element) imageNode).getAttribute("src");

                    // Handling the image path
                    Path originalPath = Paths.get(filePath);
                    Path modifiedPath = originalPath.getParent();
                    String fileNameFinal = modifiedPath.toString() + "/" + src;
                    String srcFinal = fileNameFinal.replace("\\", "/");

                    Long indentation = ((Element) imageNode).hasAttribute("indentation") ?
                            Long.parseLong(((Element) imageNode).getAttribute("indentation")) : 0L;

                    slideContents.add(new ImageContent(srcFinal, indentation));
                }

                // Create Slide object and add it to the slideList
                slideList.add(new Slide(slideContents));
            }

            return new Presentation(showTitle, slideList);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error parsing file: " + filePath);
        }

        return null;
    }

}