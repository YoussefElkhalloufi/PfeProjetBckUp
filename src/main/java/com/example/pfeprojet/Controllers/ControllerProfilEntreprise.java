package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.ChangingWindows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ControllerProfilEntreprise {

    @FXML
    private TextField confiPwdTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField idFiscaleTextField;

    @FXML
    private TextField localisationTextField;

    @FXML
    private TextField numeroFaxTextField;

    @FXML
    private TextField pwdTextField;

    @FXML
    private TextField secteurDacTextField;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField villeTextField;

    mouseEvents me = new mouseEvents();

    @FXML
    void dashboardDirecteur(ActionEvent event) throws IOException {
        ChangingWindows cw = new ChangingWindows();
        cw.switchWindow(event,"DashboardDirecteur.fxml");
    }

    @FXML
    void onMouseEntered2(MouseEvent event) {
        me.onMouseEntered2(event, updateBtn);
    }

    @FXML
    void onMouseExited2(MouseEvent event) {
        me.onMouseExited2(event, updateBtn);
    }

    @FXML
    void update(ActionEvent event) {
        //TODO : code the button !!!
    }

}
