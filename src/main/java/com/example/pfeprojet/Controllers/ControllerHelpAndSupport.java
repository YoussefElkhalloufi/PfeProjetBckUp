package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.ChangingWindows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ControllerHelpAndSupport extends mouseEvents{


    @FXML
    private Button returnButton;

    @FXML
    public void onMouseEntered(MouseEvent event) {
        onMouseEntered2(event, returnButton);
    }
    @FXML
    public void onMouseExited(MouseEvent event) {onMouseExited2(event, returnButton);}

    public void directeurDashboard(ActionEvent event) throws IOException {
        ChangingWindows cw = new ChangingWindows();
        cw.switchWindow(event, "DirecteurDashboard.fxml");
    }
}
