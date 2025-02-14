package org.example.Controller;

import org.example.Modal.*;
import org.example.View.View;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Controller {

    private Presentation presentation;
    private Fileloader fileLoader;  // Parent type
    private View view;
    String destinationFolder = "unzipped";
    private int currentSlideIndex = 0;

    public Controller(Presentation presentation, Fileloader fileLoader, View view) {
        this.presentation = presentation;
        this.fileLoader = fileLoader;
        this.view = view;

        view.addSelectButtonListener(e -> selectFile());

        view.addExitButtonListener(e -> {
            exitApplication();
        });

        view.addNextSlideButtonListener(
                e -> {showNextSlide();}
        );

        view.addPreviousSlideButtonListener(
                e -> {showPreviousSlide();});

        //Zorgt er voor als de aplicatie zomaar dicht gaat dat de unzipped folder weer leeg is
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Application is closing, cleaning up resources...");
            fileLoader.deleteFolderContents(destinationFolder);
        }));
    }

    public void selectFile() {
        String filePath = chooseFile();
        try {
            // Unzip the file and load the presentation
            fileLoader.unzip(filePath, destinationFolder);
            this.presentation = fileLoader.load(destinationFolder);
            view.setPresentation(this.presentation);
            updateSlideDisplay();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void exitApplication() {
        fileLoader.deleteFolderContents(destinationFolder);
        view.dispose();
    }

    public String chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a file");

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return "No file selected";
    }

    private void updateSlideDisplay() {
        // Remove previous content in the content panel to update it
        JPanel contentPanel = (JPanel) view.getContentPane().getComponent(1); // Get content panel
        contentPanel.removeAll();  // Remove any previous content

        // Set content panel to BoxLayout to allow vertical stretching
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        if (presentation != null && !presentation.getSlides().isEmpty()) {
            Slide currentSlide = presentation.getSlides().get(currentSlideIndex);
            String showtitle = presentation.getShowTitle();

            // Create a panel to hold the content of the current slide
            JPanel textContentPanel = new JPanel();
            textContentPanel.setLayout(new BoxLayout(textContentPanel, BoxLayout.Y_AXIS));

            // Create title label and ensure it is centered horizontally
            JLabel titleLabel = new JLabel(showtitle, SwingConstants.CENTER);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally
            titleLabel.setFont(new Font("Arial", Font.BOLD, 22)); // Optional: increase font size for better visibility
            textContentPanel.add(titleLabel);

            // Add vertical glue to center the content vertically
            textContentPanel.add(Box.createVerticalGlue());

            for (SlideContent slideContent : currentSlide.getContents()) {
                // Add text content to the panel
                if ("title".equals(slideContent.getType()) || "text".equals(slideContent.getType())) {
                    JLabel textLabel = new JLabel(slideContent.getContent(), SwingConstants.CENTER);
                    textLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally
                    textLabel.setFont(new Font("Arial", Font.BOLD, 30));
                    textContentPanel.add(textLabel);
                }

                // Check the type of the content and display image if it's an image
                if (slideContent.getSrc() != null) {
                    ImageIcon imageIcon = new ImageIcon(slideContent.getSrc());

                    // Check if the image has loaded properly (optional debug)
                    if (imageIcon.getIconWidth() > 0 && imageIcon.getIconHeight() > 0) {
                        System.out.println("Displaying image: " + slideContent.getSrc());  // Log the image source
                        JLabel imageLabel = new JLabel(imageIcon);
                        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the image horizontally
                        textContentPanel.add(imageLabel);
                    }
                }

                textContentPanel.add(Box.createVerticalStrut(10));  // Space between content items
            }

            // Add vertical glue again to ensure content is centered
            textContentPanel.add(Box.createVerticalGlue());

            // Add the text content panel to the content panel
            contentPanel.add(textContentPanel);

            // Update the slide indicator after the content
            updateSlideIndicator();

        } else {
            // If no slides are available, set a message in the content area
            JLabel noSlidesLabel = new JLabel("No slides available.", SwingConstants.CENTER);
            noSlidesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the message
            contentPanel.add(noSlidesLabel);
        }

        // Revalidate and repaint the content panel to display the changes
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void updateSlideIndicator() {
        if (presentation != null && !presentation.getSlides().isEmpty()) {
            // Update the text with the current slide index and total number of slides
            String slideText = (currentSlideIndex + 1) + "/" + presentation.getSlides().size();
            view.indicatorLabel.setText(slideText); // Update the indicator label
        }
    }

    // Method to show the next slide
    public void showNextSlide() {
        if (presentation != null && currentSlideIndex < presentation.getSlides().size() - 1) {
            currentSlideIndex++;
            updateSlideDisplay();
        }
    }

    // Method to show the previous slide
    public void showPreviousSlide() {
        if (presentation != null && currentSlideIndex > 0) {
            currentSlideIndex--;
            updateSlideDisplay();
        }
    }


    public String getDestinationFolder() {
        return  destinationFolder;
    }
}
