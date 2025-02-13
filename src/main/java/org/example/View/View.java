package org.example.View;

import org.example.Modal.Presentation;
import org.example.Modal.Slide;
import org.example.Modal.SlideContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private final JButton selectFileButton;
    private final JButton exitButton;
    private final JButton nextButton;
    private final JButton prevButton;
    private Presentation presentation;
    private int currentSlideIndex = 0;
    private JLabel titleLabel;
    private JLabel indicatorLabel;

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

        // Create a panel for the indicator at the bottom
        JPanel indicatorPanel = new JPanel();
        indicatorPanel.setBackground(Color.blue);  // Set the background color to blue

        // Create the label for the slide indicator
        indicatorLabel = new JLabel("0/0", SwingConstants.CENTER);
        indicatorLabel.setForeground(Color.white); // Change text color to white for contrast
        indicatorPanel.add(indicatorLabel);  // Add the label to the panel

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        selectFileButton = new JButton("Select File");
        nextButton = new JButton("Next Slide");
        prevButton = new JButton("Previous Slide");
        buttonPanel.add(selectFileButton);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        exitButton = new JButton("Exit Application");
        buttonPanel.add(exitButton);
        buttonPanel.setBackground(Color.darkGray);

        // Create a container panel for the South region with BoxLayout
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));  // Stack panels vertically
        southPanel.add(indicatorPanel);  // Add the indicator panel first
        southPanel.add(buttonPanel);    // Add the button panel below the indicator panel

        // Add the southPanel to the bottom of the frame
        add(southPanel, BorderLayout.SOUTH);

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

    // Update the indicator label text when navigating through slides
    private void updateSlideIndicator() {
        if (presentation != null && !presentation.getSlides().isEmpty()) {
            // Update the text with the current slide index and total number of slides
            String slideText = (currentSlideIndex + 1) + "/" + presentation.getSlides().size();
            indicatorLabel.setText(slideText); // Update the indicator label
        }
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

    public void addExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }
}
