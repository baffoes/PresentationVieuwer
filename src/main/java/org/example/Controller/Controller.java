package org.example.Controller;

import org.example.Modal.*;
import org.example.View.View;
import javax.swing.*;
import java.io.File;

public class Controller {

    private Presentation presentation;
    private Fileloader fileLoader;  // Parent type
    private View view;
    String destinationFolder = "unzipped";

    public Controller(Presentation presentation, Fileloader fileLoader, View view) {
        this.presentation = presentation;
        this.fileLoader = fileLoader;
        this.view = view;

        view.addSelectButtonListener(e -> selectFile());

        view.addExitButtonListener(e -> {
           exitAplication();
        });

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void exitAplication() {
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
}
