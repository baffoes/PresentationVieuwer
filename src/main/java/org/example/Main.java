package org.example;

import org.example.Controller.Controller;
import org.example.Modal.Presentation;
import org.example.Modal.Fileloader;
import org.example.View.View;

import java.util.Collections; // Placeholder for empty slides

public class Main {
    public static void main(String[] args) {
        // Initialize the Model with default values
        Presentation presentation = new Presentation("Default Show Title", Collections.emptyList());
        Fileloader fileLoader = new Fileloader();

        // Initialize the View
        View view = new View();

        // Initialize the Controller with the Model (Presentation), Fileloader, and View
        Controller controller = new Controller(presentation, fileLoader, view);

        // Show the View
        view.setVisible(true);
    }
}
