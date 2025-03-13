package org.example.Controller;

import org.example.Model.*;
import org.example.Utilities.DirectoryCleaner;
import org.example.Utilities.FileLoader;
import org.example.Utilities.Unzipper;
import org.example.View.View;

import javax.swing.*;
import java.io.File;

public class Controller {

    private Presentation presentation;
    private final View view;
    private final String destinationFolder = "unzipped";
    private int currentSlideIndex = 0;


    public Controller(Presentation presentation, View view) {
        this.presentation = presentation;
        this.view = view;

        // Set up listeners in the controller
        setupListeners();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Application is closing, cleaning up resources...");
            DirectoryCleaner.deleteFolderContents(destinationFolder);
        }));
    }

    public void getView(){
        view.setVisible(true);
    }

    private void setupListeners() {
        view.addSelectButtonListener(_ -> selectFile());
        view.addExitButtonListener(_ -> exitApplication());
        view.addNextSlideButtonListener(_ -> showNextSlide());
        view.addPreviousSlideButtonListener(_ -> showPreviousSlide());
        view.addGoToSlideListener(_ -> goToSlide());
    }

    private String chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a file");

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    public void selectFile() {
        // filePath getter
        String filePath = chooseFile();
        if (filePath == null) {
            JOptionPane.showMessageDialog(view, "No file selected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Delete folder content
            DirectoryCleaner.deleteFolderContents(destinationFolder);

            // Unzip the file and load the presentation
            Unzipper.unzip(filePath, destinationFolder);

            // Load the presentation
            this.presentation = new FileLoader().load(destinationFolder);
            view.setPresentation(this.presentation);
            currentSlideIndex = 0;
            updateSlideDisplay();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to load presentation!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exitApplication() {
        DirectoryCleaner.deleteFolderContents(destinationFolder);
        view.dispose();
    }

    private void updateSlideDisplay() {
        if (presentation != null && !presentation.getSlides().isEmpty()) {
            Slide currentSlide = presentation.getSlides().get(currentSlideIndex);
            view.updateSlideDisplay(currentSlide, currentSlideIndex + 1, presentation.getSlides().size());
        } else {
            view.displayNoSlidesMessage();
        }
    }

    private void showNextSlide() {
        if (currentSlideIndex < presentation.getSlides().size() - 1) {
            currentSlideIndex++;
            updateSlideDisplay();
        }
    }

    private void showPreviousSlide() {
        if (currentSlideIndex > 0) {
            currentSlideIndex--;
            updateSlideDisplay();
        }
    }

    private void goToSlide() {
        int slideIndex = view.getSlideNumberInput();
        if (slideIndex >= 0 && slideIndex < presentation.getSlides().size()) {
            currentSlideIndex = slideIndex;
            updateSlideDisplay();
        } else {
            JOptionPane.showMessageDialog(view, "Invalid slide number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
