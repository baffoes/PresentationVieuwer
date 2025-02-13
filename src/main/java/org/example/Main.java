package org.example;

import org.example.Controller.Controller;
import org.example.Modal.Fileloader;
import org.example.Modal.Presentation;
import org.example.View.View;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

            Presentation presentation = null;
            Fileloader fileloader = new Fileloader();
            View view = new View();
            Controller controller = new Controller(presentation, fileloader,view);
            view.setVisible(true);


    }
}
