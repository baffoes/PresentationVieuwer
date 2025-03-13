package org.example.View;

import org.example.Model.*;
import org.example.Model.SlideContent.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class View extends JFrame {

    private final JButton selectFileButton;
    private final JButton exitButton;
    private final JButton nextButton;
    private final JButton prevButton;
    private final JButton submitButton;
    private final JTextField slideInputField;
    private JLabel titleLabel;
    private JLabel indicatorLabel;

    public View() {
        // Set up the frame
        setTitle("Presentation Viewer");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.black);
        titleLabel = new JLabel("Presentation Viewer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setForeground(Color.white);
        titlePanel.add(titleLabel,BorderLayout.CENTER);

        exitButton = new JButton("X");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setPreferredSize(new Dimension(50, 40)); // Uniform button size
        exitButton.setBackground(Color.black); // Dark gray for modern look
        exitButton.setForeground(Color.red);
        exitButton.setFocusPainted(false);
        titlePanel.add(exitButton,BorderLayout.EAST);

        add(titlePanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        // Slide Indicator Panel
        JPanel indicatorPanel = new JPanel();
        indicatorPanel.setBackground(Color.lightGray);
        indicatorLabel = new JLabel("0/0", SwingConstants.CENTER);
        indicatorLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Arial font for the indicator
        indicatorLabel.setForeground(Color.black);
        indicatorPanel.add(indicatorLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Centered, with spacing
        buttonPanel.setBackground(Color.darkGray);

        // Buttons
        selectFileButton = new JButton("Select File");
        prevButton = new JButton("Previous Slide");
        nextButton = new JButton("Next Slide");
        submitButton = new JButton("Go to Slide");

// Slide Input Field
        slideInputField = new JTextField(2);
        slideInputField.setFont(new Font("Arial", Font.PLAIN, 16));


// Improve button styling
        JButton[] buttons = {selectFileButton, prevButton, nextButton, submitButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setPreferredSize(new Dimension(140, 40)); // Uniform button size
            button.setBackground(Color.darkGray); // Dark gray for modern look
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
        }

// Add components to panel
        buttonPanel.add(selectFileButton);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(slideInputField);
        buttonPanel.add(submitButton);


        // Bottom Panel
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(indicatorPanel);
        southPanel.add(buttonPanel);

        add(southPanel, BorderLayout.SOUTH);
    }

    // Set the presentation and update the view
    public void setPresentation(Presentation presentation) {
        titleLabel.setText(presentation.getShowTitle());
        updateSlideDisplay(presentation.getSlides().get(0), 1, presentation.getSlides().size()); // Show the first slide
    }

    public void updateSlideDisplay(Slide currentSlide, int currentSlideNumber, int totalSlides) {
        // Get the content panel and clear previous content
        JPanel contentPanel = (JPanel) getContentPane().getComponent(1);
        contentPanel.removeAll();

        // Create a panel to hold slide content, with vertical layout
        List<SlideContent> slideContents = currentSlide.getContents();
        JPanel textContentPanel = new JPanel();
        textContentPanel.setLayout(new BoxLayout(textContentPanel, BoxLayout.Y_AXIS)); // Stack content vertically

        // Add vertical glue to center the content vertically
        textContentPanel.add(Box.createVerticalGlue());

        for (SlideContent slideContent : currentSlide.getContents()) {
            // Tekstinhoud verwerken
            if (slideContent instanceof TextContent) {
                JLabel textLabel = new JLabel(slideContent.getContent(), SwingConstants.CENTER);
                textLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centreer de tekst
                textLabel.setFont(new Font("Arial", Font.BOLD, 34));
                textContentPanel.add(textLabel);
            }

            // Afbeeldingsinhoud verwerken
            if (slideContent instanceof ImageContent) {
                ImageContent imageContent = (ImageContent) slideContent;
                ImageIcon imageIcon = new ImageIcon(imageContent.getContent());

                // Controleer of de afbeelding correct is geladen
                if (imageIcon.getIconWidth() > 0 && imageIcon.getIconHeight() > 0) {
                    System.out.println("Displaying image: " + imageContent.getContent());  // Debug-log
                    JLabel imageLabel = new JLabel(imageIcon);
                    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centreer afbeelding
                    textContentPanel.add(imageLabel);
                }
            }

            textContentPanel.add(Box.createVerticalStrut(10));  // Ruimte tussen items
        }


        // Add vertical glue again to ensure content is centered
        textContentPanel.add(Box.createVerticalGlue());

        // Add the text content panel to the content panel
        contentPanel.add(textContentPanel);

        // Create a container to center the textContentPanel horizontally and vertically
        JPanel centeredPanel = new JPanel(new BorderLayout());
        centeredPanel.add(textContentPanel, BorderLayout.CENTER);

        // Add the centeredPanel to the main content area
        contentPanel.add(centeredPanel, BorderLayout.CENTER);

        // Update the slide indicator (e.g., "1/5")
        indicatorLabel.setText(currentSlideNumber + "/" + totalSlides);

        // Revalidate and repaint to apply changes
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Display a message when there are no slides
    public void displayNoSlidesMessage() {
        JPanel contentPanel = (JPanel) getContentPane().getComponent(1);
        contentPanel.removeAll();

        JLabel noSlidesLabel = new JLabel("No slides available.", SwingConstants.CENTER);
        contentPanel.add(noSlidesLabel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public int getSlideNumberInput() {
        try {
            return Integer.parseInt(slideInputField.getText()) - 1; //
        } catch (NumberFormatException e) {
            return -1; // Invalid input
        }
    }

    // Add listeners to buttons
    public void addSelectButtonListener(ActionListener listener) {
        selectFileButton.addActionListener(listener);
    }

    public void addExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    public void addNextSlideButtonListener(ActionListener listener) {
        nextButton.addActionListener(listener);
    }

    public void addPreviousSlideButtonListener(ActionListener listener) {
        prevButton.addActionListener(listener);
    }

    public void addGoToSlideListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }
}
