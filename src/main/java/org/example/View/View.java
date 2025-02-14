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
    private JLabel titleLabel;
    public JLabel indicatorLabel;

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
    }

    // Method to set the presentation and display the first slide
    public void setPresentation(Presentation presentation) {
       isVisible();
    }

    // Method to add a listener for the select file button
    public void addSelectButtonListener(ActionListener listener) {
        selectFileButton.addActionListener(listener);
    }

    public void addExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    public  void addNextSlideButtonListener(ActionListener listener) {
        nextButton.addActionListener(listener);
    }

    public void addPreviousSlideButtonListener(ActionListener listener) {
        prevButton.addActionListener(listener);
    }

}
