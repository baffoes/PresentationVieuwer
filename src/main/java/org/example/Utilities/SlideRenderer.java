package org.example.Utilities;

import org.example.Model.*;
import org.example.Model.SlideContent.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SlideRenderer {

    public static JPanel renderSlide(Slide currentSlide) {
        JPanel slidePanel = new JPanel();
        slidePanel.setLayout(new BoxLayout(slidePanel, BoxLayout.Y_AXIS));

        List<SlideContent> slideContents = currentSlide.getContents();

        // Iterate through the slide contents and render them based on type
        for (SlideContent content : slideContents) {
            if (content instanceof TitleContent) {
                slidePanel.add(renderTitle((TitleContent) content));
            } else if (content instanceof TextContent) {
                slidePanel.add(renderText((TextContent) content));
            } else if (content instanceof ImageContent) {
                slidePanel.add(renderImage((ImageContent) content));
            }
        }

        // Add some space between content items
        slidePanel.add(Box.createVerticalStrut(10));

        return slidePanel;
    }

    private static JPanel renderTitle(TitleContent titleContent) {
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(titleContent.getContent(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private static JPanel renderText(TextContent textContent) {
        JPanel textPanel = new JPanel();
        int indentationValue = (int) textContent.getIndentation();

        // Use an empty border for indentation
        int indentation = indentationValue * 10;  // Adjust this multiplier for more or less indentation
        textPanel.setLayout(new FlowLayout(FlowLayout.LEFT, indentation, 0)); // Align left and set horizontal spacing

        // Add the text with a custom font
        JLabel textLabel = new JLabel(textContent.getContent(), SwingConstants.LEFT);  // Align text to the left
        textLabel.setFont(new Font(textContent.getFont(), Font.PLAIN, 34));
        textPanel.add(textLabel);

        // Set the border for indentation effect
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, indentation, 0, 0));  // Add padding on the left

        return textPanel;
    }

    private static JPanel renderImage(ImageContent imageContent) {
        JPanel imagePanel = new JPanel();
        ImageIcon imageIcon = new ImageIcon(imageContent.getContent());

        JLabel imageLabel = new JLabel(imageIcon, SwingConstants.LEFT);  // Align image to the left
        imagePanel.add(imageLabel);

        // Indentation for the image
        int indentationValue = (int) imageContent.getIndentation();
        int indentation = indentationValue * 20;  // Adjust multiplier as needed
        imagePanel.setBorder(BorderFactory.createEmptyBorder(0, indentation, 0, 0));  // Add padding on the left

        return imagePanel;
    }
}
