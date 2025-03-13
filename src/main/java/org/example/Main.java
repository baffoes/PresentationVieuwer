package org.example;

import org.example.Controller.Controller;
import org.example.Model.Presentation;
import org.example.View.View;

import java.util.Collections; // Placeholder for empty slides

public class Main {
    public static void main(String[] args) {
        Presentation presentation = new Presentation("Default Show Title", Collections.emptyList());
        View view = new View();
        Controller controller = new Controller(presentation, view);
        controller.getView();
    }
}
