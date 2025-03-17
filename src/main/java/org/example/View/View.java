package org.example.View;

import org.example.Model.*;
import org.example.Utilities.SlideRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class View extends JFrame {

    private JButton selectFileButton;
    private JButton exitButton;
    private JButton nextButton;
    private JButton prevButton;
    private JButton submitButton;
    private JTextField slideInputField;
    private JLabel titleLabel;
    private JLabel indicatorLabel;

    public View() {
        // Set up the frame
        setTitle("Presentation Viewer");
        setSize(1600, 900);
        setLocationRelativeTo(SwingUtilities.getWindowAncestor(this));
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
        contentPanel.setLayout(new BorderLayout());  // Ensures proper layout management
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

    // Display the presentation and update the view
    public void displayPresentation(Presentation presentation) {
        titleLabel.setText(presentation.getShowTitle());
        updateSlideDisplay(presentation.getSlides().get(0), 1, presentation.getSlides().size());
    }


    public void updateSlideDisplay(Slide currentSlide, int currentSlideNumber, int totalSlides) {
        // Get the content panel and clear previous content
        JPanel contentPanel = (JPanel) getContentPane().getComponent(1);
        contentPanel.removeAll();

        // Use SlideRenderer to render the current slide
        JPanel slidePanel = SlideRenderer.renderSlide(currentSlide);

        // Create a container to center the slidePanel horizontally and vertically
        JPanel centeredPanel = new JPanel();
        centeredPanel.setLayout(new BoxLayout(centeredPanel, BoxLayout.Y_AXIS));

        // Add slidePanel to centeredPanel (preserves layout like indentation)
        centeredPanel.add(slidePanel);

        // Add the centeredPanel to the main content area
        contentPanel.add(centeredPanel, BorderLayout.CENTER);

        // Update the slide indicator
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

   // Getter for the number input
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
