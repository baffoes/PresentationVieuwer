package org.example;

import org.example.Controller.Controller;
import org.example.Modal.JsonLoader;
import org.example.Modal.Presentation;
import org.example.View.View;
import org.json.simple.parser.ParseException;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Presentation presentation = null;
        JsonLoader jsonLoader = new JsonLoader();
        View view = new View();
        Controller controller = new Controller(presentation,jsonLoader,view);
        view.setVisible(true);
    }
}