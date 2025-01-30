package org.example.Controller;

import org.example.Modal.JsonLoader;
import org.example.Modal.Presentation;
import org.example.View.View;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Controller {

    private Presentation presentation;
    private JsonLoader jsonLoader;
    private View view;

    public Controller(Presentation presentation, JsonLoader jsonLoader, View view) {
        this.presentation = presentation;
        this.jsonLoader = jsonLoader;
        this.view = view;

        view.addSelectButtonListener(e -> {
            String destinationFolder = "unzipped";
            try {
                jsonLoader.unzip(chooseFile(), destinationFolder);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Files unzipped successfully.");
            view.updateTextArea(jsonLoader.loadJson(destinationFolder));
        });

        view.addSelectButtonListener(e -> {
            String destinationFolder = "unzipped";
            try {
                jsonLoader.unzip(chooseFile(), destinationFolder);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Files unzipped successfully.");
            view.updateTextArea(jsonLoader.loadJson(destinationFolder));
        });
    }





    public String chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a file");

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return ((java.io.File) selectedFile).getAbsolutePath();
        }
        return "No file selected";
    }



}
