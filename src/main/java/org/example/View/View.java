package org.example.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private final JTextArea textArea;
    private final JButton button;
    private final JButton button2;

    public View() {
        // Set up the frame
        setTitle("My First JFrame");
        setSize(1600,900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Use BoxLayout to stack components vertically
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Create a label
        JLabel label = new JLabel("Presentation Viewer");
        label.setFont(new Font("Serif", Font.BOLD, 32)); // Make the label larger
        label.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the label
        add(label);

        // Create a button
        button = new JButton("Select File");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the button
        add(button);

         button2= new JButton("Select File");
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the button
        add(button2);

        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        add(new JScrollPane(textArea));

        setResizable(false);
    }

    public void addSelectButtonListener(ActionListener listener) {
        button.addActionListener(listener);
    }

    public void addTestButtonListener(ActionListener listener) {
        button.addActionListener(listener);
    }

    // Method to update the displayed JSON content
    public void updateTextArea(String content) {
        textArea.setText(content);
    }

    public void showWindow() {
        setVisible(true);
    }

}
