package org.example.View;

import org.example.Modal.Presentation;
import org.example.Modal.Slide;
import org.example.Modal.SlideContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private final JButton selectFileButton;
    private final JButton nextButton;
    private final JButton prevButton;
    private Presentation presentation;
    private int currentSlideIndex = 0;
    private JLabel titleLabel;  // Store the title label here

    public View() {
        // Set up the frame
        setTitle("Presentation Viewer");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Panel (North)
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.darkGray);

        // Create and store the label for later updates
        titleLabel = new JLabel("Presentation Viewer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(Color.black);
        titlePanel.add(titleLabel);  // Add label to the title panel
        add(titlePanel, BorderLayout.NORTH);

        // Content Panel (Center)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());  // Use BorderLayout for content area
        add(contentPanel, BorderLayout.CENTER);

        // Button Panel (South)
        JPanel buttonPanel = new JPanel();
        selectFileButton = new JButton("Select File");
        nextButton = new JButton("Next Slide");
        prevButton = new JButton("Previous Slide");
        buttonPanel.add(selectFileButton);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.setBackground(Color.darkGray);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners for navigation buttons
        nextButton.addActionListener(e -> showNextSlide());
        prevButton.addActionListener(e -> showPreviousSlide());
    }

    private void updateSlideDisplay() {

        // Remove previous content in the content panel to update it
        JPanel contentPanel = (JPanel) getContentPane().getComponent(1); // Get content panel
        contentPanel.removeAll();  // Remove any previous content

        // Set content panel to BoxLayout to allow vertical stretching
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        if (presentation != null && !presentation.getSlides().isEmpty()) {
            Slide currentSlide = presentation.getSlides().get(currentSlideIndex);
            String showtitle = presentation.getShowTitle();


            // Create a panel to hold the content of the current slide
            JPanel textContentPanel = new JPanel();
            textContentPanel.setLayout(new BoxLayout(textContentPanel, BoxLayout.Y_AXIS));

            // Add vertical glue to center the content vertically
            textContentPanel.add(Box.createVerticalGlue());

            JLabel titleLabel = new JLabel(showtitle);
            contentPanel.add(titleLabel);

            for (SlideContent slideContent : currentSlide.getContents()) {
                // Add text content to the panel
                if ("title".equals(slideContent.getType()) || "text".equals(slideContent.getType())) {
                    JLabel textLabel = new JLabel(slideContent.getContent(), SwingConstants.CENTER);
                    textLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally
                    textLabel.setFont(new Font("Arial", Font.BOLD, 30));
                    textContentPanel.add(textLabel);
                }

                // Check the type of the content and display image if it's an image
                if (slideContent.getSrc().endsWith(".jpg")) {
                    System.out.println("Displaying image: " + slideContent.getSrc());  // Log the image source

                    ImageIcon imageIcon = new ImageIcon(slideContent.getSrc());

                    // Check if the image has loaded properly (optional debug)
                    if (imageIcon.getIconWidth() > 0 && imageIcon.getIconHeight() > 0) {
                        JLabel imageLabel = new JLabel(imageIcon);
                        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the image horizontally
                        textContentPanel.add(imageLabel);
                    } else {
                        System.out.println("Failed to load image: " + slideContent.getSrc());
                    }
                }

                textContentPanel.add(Box.createVerticalStrut(10));  // Space between content items
            }

            // Add vertical glue again to ensure content is centered
            textContentPanel.add(Box.createVerticalGlue());

            // Add the text content panel to the content panel
            contentPanel.add(textContentPanel);

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

    // Method to show the next slide
    private void showNextSlide() {
        if (presentation != null && currentSlideIndex < presentation.getSlides().size() - 1) {
            currentSlideIndex++;
            updateSlideDisplay();
        }
    }

    // Method to show the previous slide
    private void showPreviousSlide() {
        if (presentation != null && currentSlideIndex > 0) {
            currentSlideIndex--;
            updateSlideDisplay();
        }
    }

    // Method to set the presentation and display the first slide
    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
        currentSlideIndex = 0;
        updateSlideDisplay();
    }

    // Method to add a listener for the select file button
    public void addSelectButtonListener(ActionListener listener) {
        selectFileButton.addActionListener(listener);
    }
}
